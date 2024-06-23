package com.aluracursos.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutores(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaMuerte
) {
}
