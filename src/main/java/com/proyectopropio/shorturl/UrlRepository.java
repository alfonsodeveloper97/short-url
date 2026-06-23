package com.proyectopropio.shorturl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, String> {
    // No hace falta escribir nada aquí dentro.
    // Al heredar de JpaRepository pasándole <LaEntidad, ElTipoDeSuId>,
    // Spring ya sabe hacer de todo: save(), findById(), delete(), etc.
}