package com.gabriel.carrito.core.document;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Document(collection = "Libro")
@Getter
@Setter
public class Libro {
    
    @Id
    private String id;
    @NotNull
    private String isbn;
    @NotNull
    private String nombre;
    @NotNull
    private String autor;
    @NotNull
    private String año;
    @NotNull
    private String editorial;
    @NotNull
    private String genero;
    @NotNull
    private String sinopsis;
    @NotNull
    private Integer disponibles;
    @NotNull
    private Double precio;
}
