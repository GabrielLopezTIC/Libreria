package com.gabriel.carrito.core.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.carrito.core.security.document.Rol;
import com.gabriel.carrito.core.security.enums.RolNombre;

import java.util.Optional;

@Repository
public interface RolRepository extends MongoRepository<Rol, String> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
    Optional<Rol> findByRolNombre(String rolNombre);
}
