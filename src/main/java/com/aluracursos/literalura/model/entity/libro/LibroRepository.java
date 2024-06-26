package com.aluracursos.literalura.model.entity.libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTitulo(String titulo);

    List<Libro> findByIdioma(String idioma);
}

