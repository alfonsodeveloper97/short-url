package com.proyectopropio.shorturl; // Tu package real

import com.proyectopropio.shorturl.UrlMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RestController
public class UrlController {

    // 1. Inyectamos el repositorio en lugar del HashMap en memoria
    private final UrlRepository urlRepository;

    // El constructor sirve para que Spring Boot nos dé el repositorio listo para usar
    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping("/acortar")
    public ResponseEntity<Map<String, String>> acortarUrl(@RequestBody Map<String, String> request) {
        String urlLarga = request.get("urlLarga");
        if (urlLarga == null || urlLarga.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "La URL larga es obligatoria"));
        }

        // Generamos un ID corto único de 6 caracteres
        String idCorto = UUID.randomUUID().toString().substring(0, 6);

        // 2. Guardamos en la Base de Datos H2 en vez del HashMap
        UrlMapping mapping = new UrlMapping(idCorto, urlLarga);
        urlRepository.save(mapping);

        String urlAcortada = "http://localhost:8080/" + idCorto;
        return ResponseEntity.ok(Map.of("urlAcortada", urlAcortada));
    }

    @GetMapping("/{idCorto}")
    public Object redireccionar(@PathVariable String idCorto) {
        Optional<UrlMapping> mappingOptional = urlRepository.findById(idCorto);

        if (mappingOptional.isPresent()) {
            UrlMapping mapping = mappingOptional.get();

            // ⏳ COMPROBACIÓN DE CADUCIDAD: ¿Tiene más de 2 minutos de vida?
            //Verificamos que la fechaCreacion NO sea null antes de operar
            // (Para producción podrías cambiar .plusMinutes(2) por .plusDays(7))
            if (mapping.getFechaCreacion() !=null) {
                LocalDateTime fechaCaducidad = mapping.getFechaCreacion().plusDays(7);
                System.out.println("🕒 Hora actual: " + LocalDateTime.now());
                System.out.println("⏳ Hora caducidad: " + fechaCaducidad);
                if (LocalDateTime.now().isAfter(fechaCaducidad)) {
                    System.out.println("❌ ENLACE CADUCADO - Redirigiendo a /expired");
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .location(URI.create("http://localhost:8080/expired"))
                            .build();
                }
            }



            // Si NO ha caducado, sumamos el clic y registramos la fecha
            System.out.println("✅ ENLACE VÁLIDO - Redirigiendo a URL original");
            mapping.setClics(mapping.getClics() + 1);
            mapping.setFechaUltimoClic(LocalDateTime.now()); // 🕒 Registramos la hora exacta del clic

            urlRepository.save(mapping);

            String urlLarga = mapping.getUrlLarga();
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(urlLarga))
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("http://localhost:8080/404"))
                    .build();
        }
    }
}