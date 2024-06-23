package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.entity.autor.Autor;
import com.aluracursos.literalura.model.entity.autor.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor buscarAutorPorNombre(String nombre) {
        return autorRepository.findByNombre(nombre);
    }

    public void guardarAutor(Autor autor) {
        autorRepository.save(autor);
    }

    public boolean existeAutorPorNombre(String nombre) {
        return autorRepository.existsByNombre(nombre);
    }
}

