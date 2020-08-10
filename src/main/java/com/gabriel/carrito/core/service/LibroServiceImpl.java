package com.gabriel.carrito.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.carrito.core.document.Libro;
import com.gabriel.carrito.core.repository.LibroRepo;

@Service
public class LibroServiceImpl{
    
    @Autowired
    private LibroRepo libroRepo;

 
    public Optional<Libro> save(Libro libro) {
	Optional<Libro> li = libroRepo.findByNombre(libro.getNombre());
	if(li.isPresent()) {
	    return Optional.empty();
	}else {
	    return Optional.of(libroRepo.save(libro));
	}
    }

 
   public Optional<Libro> findByName(String nombre) {
	return libroRepo.findByNombre(nombre);
    }

   
    public Optional<List<Libro>> findAll() {
	return Optional.of(libroRepo.findAll());
    }

   
    public Optional<Libro> update(Libro libro) {
	    return Optional.of(libroRepo.save(libro));
    }

  
    public boolean delete(String nombre) {
	Optional<Libro> li = libroRepo.findByNombre(nombre);
	if(li.isPresent()) {
	    libroRepo.delete(li.get());
	    return true;
	}else {
	   return false;
	}
    }

  
    public Optional<Libro> findByIsbn(String isbn) {
	return libroRepo.findByIsbn(isbn);
    }

}
