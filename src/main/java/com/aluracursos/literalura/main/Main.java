package com.aluracursos.literalura.main;

import com.aluracursos.literalura.model.dto.DatosAutores;
import com.aluracursos.literalura.model.dto.DatosBusqueda;
import com.aluracursos.literalura.model.dto.DatosLibro;
import com.aluracursos.literalura.model.entity.autor.Autor;
import com.aluracursos.literalura.model.entity.autor.AutorRepository;
import com.aluracursos.literalura.model.entity.libro.Libro;
import com.aluracursos.literalura.model.entity.libro.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.conversor.ConvierteDatos;
import com.aluracursos.literalura.service.LibroService;
import com.aluracursos.literalura.service.AutorService;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    protected Scanner sc = new Scanner(System.in);
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private final LibroService libroService;
    private final AutorService autorService;

    // Constructor para inyectar los servicios de libro y autor
    public Main(AutorRepository autorRepository, LibroRepository libroRepository, LibroService libroService, AutorService autorService) {
        this.autorRepository = autorRepository;
        this.libroRepository=libroRepository;
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ****************************
                    Bienvenido a LiterAlura
                    1 - Agregar un libro a favoritos

                    0 - Salir
                    ****************************
                    Por favor elige una opción:
                    """;
            System.out.println(menu);
            try {
                opcion = sc.nextInt();
                sc.nextLine(); // Consumir el salto de línea pendiente
            } catch (InputMismatchException e) {
                opcion = 999;
                sc.nextLine(); // Consumir el salto de línea pendiente
            }

            switch (opcion) {
                case 1:
                    buscarLibroAPI();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("\nOpción inválida, por favor intenta de nuevo\n");
                    break;
            }
        }
    }

    private void buscarLibroAPI() {
        DatosBusqueda datos = getDatosBusqueda();

        datos.resultado().stream()
                .findFirst()
                .ifPresentOrElse(primerLibro -> {
                            System.out.println("Primer libro encontrado:");
                            System.out.println(primerLibro);

                            System.out.println("Si el libro no es el que buscabas intenta ser más específico en tu próxima búsqueda.\n");
                            System.out.println("¿Deseas agregar este libro a tus favoritos? (Ingresa 1)");
                            String respuesta = sc.nextLine();
                            if (respuesta.equalsIgnoreCase("1")) {
                                try {
                                    // Convertir DatosLibro a Libro
                                    Libro libro = convertirDatosLibro(primerLibro);

                                    // Validar si el libro ya existe antes de guardarlo
                                    if (!libroService.existeLibroPorTitulo(libro.getTitulo())) {
                                        // Guardar el libro en la base de datos
                                        libroService.guardarLibro(libro);

                                        System.out.println("Libro agregado a tus favoritos.\n");
                                    } else {
                                        System.out.println("El libro ya existe en la base de datos.\n");
                                    }
                                } catch (RuntimeException e) {
                                    System.out.println("Error al intentar agregar el libro: " + e.getMessage());
                                }
                            } else {
                                System.out.println("El libro no se agregó a favoritos.\n");
                            }
                        },
                        () -> System.out.println("No se encontraron libros que coincidieran con tu búsqueda.\n")
                );
    }

    private DatosBusqueda getDatosBusqueda() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = sc.nextLine();
        System.out.println("Buscando ...");
        String URL_BASE = "https://gutendex.com/books/?search=";
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        return conversor.obtenerDatos(json, DatosBusqueda.class);
    }

    private Libro convertirDatosLibro(DatosLibro datosLibro) {
        // Obtener o guardar el autor según corresponda
        Autor autor = obtenerOGuardarAutor(datosLibro.primerAutor());

        // Crear el libro
        Libro libro = new Libro();
        libro.setTitulo(datosLibro.titulo());
        libro.setAutor(autor);
        libro.setIdioma(datosLibro.idiomas().isEmpty() ? "" : datosLibro.idiomas().get(0));
        libro.setNumeroDescargas(datosLibro.numeroDescarga());

        return libro;
    }

    private Autor obtenerOGuardarAutor(DatosAutores datosAutor) {
        Autor autor = null;

        // Verificar si el autor ya está en la base de datos
        if (datosAutor != null) {
            autor = autorService.buscarAutorPorNombre(datosAutor.nombre());
        }

        // Si no existe, crear uno nuevo y guardarlo
        if (autor == null) {
            autor = new Autor(datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaMuerte());
            autorService.guardarAutor(autor);
        }

        return autor;
    }
}
