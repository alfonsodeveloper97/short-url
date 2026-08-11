package com.proyectopropio.shorturl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, String> {
    //Permite buscar un registro utilizando una URL larga
    Optional<UrlMapping> findByUrlLarga(String UrlLarga);
}