package com.gabriel.carrito.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.carrito.core.security.document.Usuario;
import com.gabriel.carrito.core.security.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void save(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public  Optional<List<Usuario>> findAll() {
	return Optional.of(usuarioRepository.findAll());
    }

    public boolean deleteByNombreUsuario(String nombreUsuario) {
	try {
	    usuarioRepository.deleteByNombreUsuario(nombreUsuario);
	    return true;
	}catch(Exception e) {
	    log.error("Error en deletbynombreusuario: "+e);
	    return false;
	}
    }

    public Optional<Usuario> updateUsuario(Usuario usuario){
	if(!usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
	    return Optional.empty();
	}
	return Optional.of(usuarioRepository.save(usuario));
	
    }

}
