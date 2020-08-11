package com.gabriel.carrito.core.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.carrito.core.document.Libro;
import com.gabriel.carrito.core.repository.LibroRepo;
import com.gabriel.carrito.core.service.LibroServiceImpl;


@RestController
@RequestMapping("/libro")
@CrossOrigin
public class LibroController {
    @Autowired
    private LibroServiceImpl libroService;
    @Autowired
    private LibroRepo libroRepo;
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid Libro libro){
	System.out.println("creando libro");
	Optional<Libro> lib = libroService.save(libro);
	if(lib.isPresent()) {
	    return ResponseEntity.status(HttpStatus.CREATED).body(lib.get());
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Error al guardar libro");
	}
    }
    @GetMapping("/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable(value = "name") String name){
	Optional<Libro> lib = libroService.findByName(name);
	if(lib.isPresent()) {
	    return ResponseEntity.status(HttpStatus.OK).body(lib.get());
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libro no encontrado");
	}
    }
    
    @GetMapping("/findByIsbn/{isbn}")
    public ResponseEntity<?> findByIsbn(@PathVariable(value = "isbn") String isbn){
	Optional<Libro> lib = libroService.findByIsbn(isbn);
	if(lib.isPresent()) {
	    return ResponseEntity.status(HttpStatus.OK).body(lib.get());
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libro no encontrado");
	}
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
	Optional<List<Libro>> libros = libroService.findAll();
	if(libros.isPresent()) {
	    return ResponseEntity.status(HttpStatus.OK).body(libros.get());
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existen libros");
	}
    }
    
    @PutMapping("/updateByName/{name}")
    public ResponseEntity<?> updateByName(@PathVariable(value = "name") String name,@RequestBody @Valid Libro libro){
	Optional<Libro> lib = libroService.findByName(name);
	if(lib.isPresent()) {
	    lib.get().setNombre(libro.getNombre());
	    lib.get().setAutor(libro.getAutor());
	    lib.get().setIsbn(libro.getIsbn());
	    lib.get().setAño(libro.getAño());
	    lib.get().setEditorial(libro.getEditorial());
	    lib.get().setGenero(libro.getGenero());
	    lib.get().setSinopsis(libro.getSinopsis());
	    
	    return ResponseEntity.status(HttpStatus.OK).body(libroService.update(lib.get()));
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El libro no existe");
	}
    }
    
    @GetMapping("/disminuye/{libro}/cantidad/{cantidad}")
    public ResponseEntity<?> disminuyeDisponibles(@PathVariable(name="libro") String libro,@PathVariable(name = "cantidad")Integer cantidad){
	Libro lib = libroService.findByName(libro).get();
	lib.setDisponibles(lib.getDisponibles() - cantidad);
	System.out.println("disponibles: "+String.valueOf(lib.getDisponibles()));
	
	return ResponseEntity.status(HttpStatus.OK).body(libroRepo.save(lib));
    }
    
    
    @PutMapping("/updateByIsbn/{isbn}")
    public ResponseEntity<?> updateByIsbn(@PathVariable(value = "isbn") String isbn,@RequestBody @Valid Libro libro){
	Optional<Libro> lib = libroService.findByIsbn(isbn);
	if(lib.isPresent()) {
	    lib.get().setNombre(libro.getNombre());
	    lib.get().setAutor(libro.getAutor());
	    lib.get().setIsbn(libro.getIsbn());
	    lib.get().setAño(libro.getAño());
	    lib.get().setEditorial(libro.getEditorial());
	    lib.get().setGenero(libro.getGenero());
	    lib.get().setSinopsis(libro.getSinopsis());
	    
	    return ResponseEntity.status(HttpStatus.OK).body(libroService.update(lib.get()));
	}else {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El libro no existe");
	}
    }
    
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable(value="name") String name){
	if(libroService.delete(name)) {
	    System.out.println("Libro eliminado");
	    return ResponseEntity.status(HttpStatus.OK).build();
	}else {
	    System.out.println("Error al eliminar");
	    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}
    }
    
    
    

}
