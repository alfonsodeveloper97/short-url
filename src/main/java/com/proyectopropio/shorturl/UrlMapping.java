package com.proyectopropio.shorturl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UrlMapping {

    @Id
    private String id; // Este será el código corto (ej: "a1b2c3") y la Clave Primaria (Id)
    private String urlLarga; // Aquí guardaremos la URL original completa

    // ⚠️ Constructor vacío: Es OBLIGATORIO para que Hibernate funcione entre bambalinas
    public UrlMapping() {
    }

    // Constructor cómodo para crear el objeto en una sola línea
    public UrlMapping(String id, String urlLarga) {
        this.id = id;
        this.urlLarga = urlLarga;
    }

    // Getters y Setters: Obligatorios para que Spring pueda leer y escribir los datos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlLarga() {
        return urlLarga;
    }

    public void setUrlLarga(String urlLarga) {
        this.urlLarga = urlLarga;
    }
}