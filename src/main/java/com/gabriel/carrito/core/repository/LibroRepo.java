package com.gabriel.carrito.core.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.carrito.core.document.Libro;


@Repository
public interface LibroRepo extends MongoRepository<Libro,String>{
  Optional<Libro> findByNombre(String nombre);
    
    Optional<Libro> findByIsbn(String isbn);
   
}
