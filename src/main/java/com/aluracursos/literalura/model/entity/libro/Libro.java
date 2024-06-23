package com.aluracursos.literalura.model.entity.libro;

import com.aluracursos.literalura.model.entity.autor.Autor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libros")
@NoArgsConstructor
@Getter
@Setter
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer numeroDescargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;


    @Override
    public String toString() {
        return String.format("""
                                ------------------------------
                                Id: %s
                                ------------------------------
                                Título: %s
                                Idioma: %s
                                Numero de descargas: %s
                                
                                Autor: %s
                                ------------------------------
                                """,
                id,titulo, idioma, numeroDescargas, autor.getNombre());
    }

}
