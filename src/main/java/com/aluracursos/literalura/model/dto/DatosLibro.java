package com.aluracursos.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutores> datosAutores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Integer numeroDescarga
) {
        public DatosAutores primerAutor() {
                return datosAutores != null && !datosAutores.isEmpty() ? datosAutores.getFirst() : null;
        }

        @Override
        public String toString() {
                String autor = (primerAutor() != null) ? Objects.requireNonNull(primerAutor()).nombre() : "Desconocido";
                return String.format("""
                                ------------------------------
                                Título: %s
                                Autor: %s
                                Idiomas: %s
                                Número de descargas: %d
                                ------------------------------
                                """,
                        titulo, autor, idiomas, numeroDescarga);
        }
}


