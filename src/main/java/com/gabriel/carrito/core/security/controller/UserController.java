package com.gabriel.carrito.core.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.carrito.core.dto.Mensaje;
import com.gabriel.carrito.core.security.document.Rol;
import com.gabriel.carrito.core.security.document.Usuario;
import com.gabriel.carrito.core.security.dto.NuevoUsuario;
import com.gabriel.carrito.core.security.enums.RolNombre;
import com.gabriel.carrito.core.security.service.RolService;
import com.gabriel.carrito.core.security.service.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UserController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    
    /**
     * Endpoint para la alta de usuarios
     * @param nuevoUsuario
     * @param bindingResult
     * @return
     */
    @PostMapping("/createAdmin")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
	
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
       
        Set<Rol> roles = new HashSet<>();
       
        //asignacion de roles
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        if(nuevoUsuario.getRoles().contains("cliente"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_CLIENTE).get());
       
        
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Usuario guardado con exito"));
    }
    
    @PostMapping("/createCliente")
    public ResponseEntity<?> nuevoCliente(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
	
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));
       
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolNombre(RolNombre.ROLE_CLIENTE).get());
       
        
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Mensaje("Usuario guardado con exito"));
    }
    
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
	Optional<List<Usuario>> usuarios = usuarioService.findAll();
	if(!usuarios.isPresent())
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("No se cargaron los usuarios"));
	 return ResponseEntity.status(HttpStatus.OK).body(usuarios.get());
    }

    @GetMapping("/find/{nombreUsuario}")
    public ResponseEntity<?> find(@PathVariable(name = "nombreUsuario") String nombreUsuario){
	Optional<Usuario> usuario = usuarioService.getByNombreUsuario(nombreUsuario);
	if(usuario.isPresent()) {
	    return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Mensaje("No se encontro el usuario"));
	}
    }
    
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable(name = "username") String username){
	if(usuarioService.deleteByNombreUsuario(username)) {
	    return ResponseEntity.status(HttpStatus.OK).body(new Mensaje("Alumno "+username+" eliminado"));
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new Mensaje("Error al eliminar alumno"));
	}
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<?> update(@PathVariable(name = "username") String username,
	    @Valid @RequestBody NuevoUsuario nuevoUsuario,BindingResult bindingResult){
	
	if(usuarioService.existsByNombreUsuario(username)) {
	   try {
	       Usuario usuario= usuarioService.getByNombreUsuario(username).get();
	   
  
	    usuario.setNombre(nuevoUsuario.getNombre());
	    usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
	    usuario.setEmail(nuevoUsuario.getEmail());
	    
	    if(nuevoUsuario.getPassword() != null && nuevoUsuario.getPassword().length() != 0) {
		usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
		
	    }
	   
	    Set<Rol> roles = new HashSet<>();
	 
	        
	        //asignacion de roles
	        if(nuevoUsuario.getRoles().contains("admin"))
	            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
	        if(nuevoUsuario.getRoles().contains("medico"))
	            roles.add(rolService.getByRolNombre(RolNombre.ROLE_CLIENTE).get());
	        
	        
	    usuario.setRoles(roles);
	    usuarioService.save(usuario);
	    return ResponseEntity.status(HttpStatus.OK).body(usuario);
	   }catch(Exception e) {
	       log.error("Error al actualizar usuario: "+e);
	       return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Mensaje("No se pudo modificar el usuario"));
	   }
	}
	return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Mensaje("El usuario no existe"));
    }
}
