package com.proyectopropio.shorturl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class UrlMapping {

    @Id
    private String id; // Este será el código corto (ej: "a1b2c3") y la Clave Primaria (Id)

    @Column(nullable = false)
    private String urlLarga; // Aquí guardaremos la URL original completa

    private int clics = 0;

    // 📅 NUEVOS CAMPOS: Guardan las fechas en la base de datos
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime fechaUltimoClic;

    // ⚠️ Constructor vacío: Es OBLIGATORIO para que Hibernate funcione entre bambalinas
    public UrlMapping() {
    }

    // Constructor cómodo para crear el objeto en una sola línea
    public UrlMapping(String id, String urlLarga) {
        this.id = id;
        this.urlLarga = urlLarga;
        this.clics = 0;
        this.fechaCreacion = LocalDateTime.now(); // 🕒 Guarda el momento exacto de la creación
    }

    // --- Getters y Setters ---

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

    public int getClics() {
        return clics;
    }

    public void setClics(int clics) {
        this.clics = clics;
    }

    // 📅 NUEVOS GETTERS Y SETTERS
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaUltimoClic() {
        return fechaUltimoClic;
    }

    public void setFechaUltimoClic(LocalDateTime fechaUltimoClic) {
        this.fechaUltimoClic = fechaUltimoClic;
    }
}