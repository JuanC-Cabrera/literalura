package com.aluracursos.literalura.model.entity.autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    boolean existsByNombre(String nombre);

    Autor findByNombre(String nombre);
}

