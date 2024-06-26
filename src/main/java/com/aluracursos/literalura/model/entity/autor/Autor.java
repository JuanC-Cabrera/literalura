package com.aluracursos.literalura.model.entity.autor;

import com.aluracursos.literalura.model.entity.libro.Libro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "autores")
@NoArgsConstructor
@Getter
@Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private int fechaNacimiento;
    private int fechaMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(String nombre, Integer fechaNacimiento, Integer fechaMuerte) {
        this.nombre= nombre;
        this.fechaNacimiento=fechaNacimiento;
        this.fechaMuerte=fechaMuerte;
    }

    @Override
    public String toString() {
        return String.format("""
                               
                                Id: %s
                                ------------------------------
                                Nombre: %s
                                Fecha de nacimiento: %s
                                Fecha de fallecimiento: %s
                                
                                Libros:""",
                id,nombre, fechaNacimiento, fechaMuerte);
    }
}


