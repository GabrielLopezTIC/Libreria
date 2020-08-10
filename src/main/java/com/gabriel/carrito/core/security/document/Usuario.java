package com.gabriel.carrito.core.security.document;


import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Document(collection = "Usuarios")
@Getter
@Setter
public class Usuario {
   
    @Id
    private String id;
    @NotNull
    private String nombre;
    @NotNull
    private String nombreUsuario;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Set<Rol> roles;
  
    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email,
	    @NotNull String password) {
	super();
	this.nombre = nombre;
	this.nombreUsuario = nombreUsuario;
	this.email = email;
	this.password = password;
    }
  

}
