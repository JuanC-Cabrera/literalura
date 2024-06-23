package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.entity.autor.Autor;
import com.aluracursos.literalura.model.entity.autor.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor buscarAutorPorNombre(String nombre) {
        return autorRepository.findByNombre(nombre);
    }

    public void guardarAutor(Autor autor) {
        autorRepository.save(autor);
    }

    public List<Autor> listarAutoresConLibros() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(autor -> autor.getLibros().size()); // Forzar inicialización de la colección
        return autores;
    }

    public List<Autor> encontrarAutoresVivosEnAnio(int anio) {
        return autorRepository.findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(anio, anio);
    }
}

