package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.entity.libro.Libro;
import com.aluracursos.literalura.model.entity.libro.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public boolean existeLibroPorTitulo(String titulo) {
        return libroRepository.existsByTitulo(titulo);
    }

    public void guardarLibro(Libro libro) {
        libroRepository.save(libro);
    }
}

