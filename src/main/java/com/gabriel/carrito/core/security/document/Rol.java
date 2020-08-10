package com.gabriel.carrito.core.security.document;


import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Roles")
@Getter
@Setter
public class Rol {
    @Id
    private String id;
    @NotNull
    private String rolNombre;
  
}
