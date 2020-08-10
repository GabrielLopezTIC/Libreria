package com.gabriel.carrito.core.security.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.carrito.core.dto.Mensaje;
import com.gabriel.carrito.core.security.document.Rol;
import com.gabriel.carrito.core.security.enums.RolNombre;
import com.gabriel.carrito.core.security.service.RolService;

@RestController
@RequestMapping("/roles")
public class RoleController {
    

    @Autowired
    RolService rolService;
    
    /**
     * nombre,nombreUsuario,email,password,roles[]
     * @param rol
     * @param bind
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRol(@Valid @RequestBody Rol rol,BindingResult bind) {
	if(bind.hasErrors())
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("Estructura del json incorrecta o campos mal puestos"));
	if(rolService.getByRolNombre(rol.getRolNombre()).isPresent())
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Mensaje("El rol ya existe"));
	
	
	if(rol.getRolNombre().contains("admin"))
           rol.setRolNombre(RolNombre.ROLE_ADMIN.toString());
	if(rol.getRolNombre().contains("medico"))
	           rol.setRolNombre(RolNombre.ROLE_CLIENTE.toString());
	
	
	if(rolService.save(rol) == false)
	    ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new Mensaje("No se guardo el rol"));
	return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Rol guardado"));
    }
    
    
    @GetMapping("/findAll")
    public ResponseEntity<?> roles(){
	Optional<List<Rol>> roles = rolService.findAll();
	if(!roles.isPresent())
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("No se cargaron los roles"));
	 return ResponseEntity.status(HttpStatus.OK).body(roles.get());
    }

   



}
