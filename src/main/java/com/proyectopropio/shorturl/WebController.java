package com.proyectopropio.shorturl; // Tu package real

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller // Usamos @Controller (no @RestController) porque vamos a enviar páginas HTML
public class WebController {

    private final UrlRepository urlRepository;

    public WebController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // 1. Mostrar la página principal cuando entremos en http://localhost:8080/
    @GetMapping("/")
    public String mostrarInicio() {
        return "index"; // Spring buscará el archivo index.html en templates
    }

    // 2. Procesar el formulario cuando el usuario pulse el botón verde de "Acortar Enlace"
    @PostMapping("/acortar-web")
    public String acortarDesdeWeb(@RequestParam String urlLarga, Model model) {

        // Generamos el ID corto idéntico a como lo hacíamos antes
        String idCorto = UUID.randomUUID().toString().substring(0, 6);

        // Guardamos en la misma base de datos H2
        UrlMapping mapping = new UrlMapping(idCorto, urlLarga);
        urlRepository.save(mapping);

        String urlAcortada = "http://localhost:8080/" + idCorto;

        // Le pasamos la URL acortada al HTML para que la pinte en la caja azul
        model.addAttribute("urlAcortada", urlAcortada);

        return "index"; // Volvemos a mostrar la página index.html pero ahora con el resultado
    }
}