package com.proyectopropio.shorturl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UrlController {

    // Nuestra "Base de datos" temporal en memoria
    // Guarda pares de tipo <CódigoCorto, UrlLarga>
    private final Map<String, String> urlDiccionario = new HashMap<>();

    // 1. RUTA PARA ACORTAR LA URL
    // El usuario enviará la URL larga en el cuerpo (body) de la petición
    @PostMapping("/acortar")
    public String acortarUrl(@RequestBody String urlLarga) {

        // Generamos un código aleatorio único usando UUID de Java y nos quedamos con los primeros 6 caracteres
        String codigoCorto = UUID.randomUUID().toString().substring(0, 6);

        // Guardamos la relación en nuestro diccionario
        urlDiccionario.put(codigoCorto, urlLarga);

        // Le devolvemos al usuario su URL corta lista para usar
        return "Tu URL acortada es: http://localhost:8080/" + codigoCorto;
    }

    // 2. RUTA PARA REDIRIGIR
    // Cuando alguien entre a http://localhost:8080/codigoCorto
    @GetMapping("/{codigo}")
    public ResponseEntity<Void> redirigirUrl(@PathVariable String codigo) {

        // Buscamos el código en nuestro diccionario
        String urlOriginal = urlDiccionario.get(codigo);

        // Si el código existe, redirigimos al usuario usando las cabeceras HTTP
        if (urlOriginal != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(urlOriginal))
                    .build();
        } else {
            // Si el código no existe en nuestra memoria, devolvemos un error 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }
}