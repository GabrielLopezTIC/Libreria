package com.gabriel.carrito.core.document;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gabriel.carrito.core.dto.VentaItem;
import com.gabriel.carrito.core.security.document.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Venta")
public class Venta {
    @Id
    private String id;
    @NotNull
    private String codigoVenta;
    private Usuario cliente;
    @NotNull
    private VentaItem[] libros;
    @NotNull
    private String fechaVenta;
    @NotNull
    private Double costoTotal;
     
} 
