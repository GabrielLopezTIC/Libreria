package com.gabriel.carrito.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.carrito.core.security.document.Rol;
import com.gabriel.carrito.core.security.enums.RolNombre;
import com.gabriel.carrito.core.security.repository.RolRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }
    
    public Optional<Rol> getByRolNombre(String rolNombre){
	return rolRepository.findByRolNombre(rolNombre);
    }
    
    public boolean save(Rol rol){
	try {
	    rolRepository.save(rol);
	    log.info("Rol guardado");
	    return true;
	}catch(Exception e) {
	    log.error("Error en rol service:save - "+e);
	    return false;
	}
        
    }

    public  Optional<List<Rol>> findAll() {
	return Optional.of(rolRepository.findAll());
    }

}
