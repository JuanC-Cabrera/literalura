package com.aluracursos.literalura.model.entity.libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTitulo(String titulo);

}

