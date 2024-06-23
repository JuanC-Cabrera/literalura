package com.aluracursos.literalura.service.conversor;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

