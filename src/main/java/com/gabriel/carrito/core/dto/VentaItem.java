package com.gabriel.carrito.core.dto;

import com.gabriel.carrito.core.document.Libro;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VentaItem {
    private Libro libro;
    private Integer cantidad;
}
