package com.gabriel.carrito.core.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.carrito.core.document.Venta;
import com.gabriel.carrito.core.security.jwt.JwtProvider;
import com.gabriel.carrito.core.security.repository.UsuarioRepository;
import com.gabriel.carrito.core.service.VentaServiceImpl;

@CrossOrigin
@RestController
@RequestMapping("/venta")
public class VentaController {
    @Autowired
    private VentaServiceImpl ventaService;
    @Autowired
    private JwtProvider provider;
    @Autowired
    private UsuarioRepository usuarioRepo;
    
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody Venta venta,  @RequestHeader("Authorization") String token){
	String tokenPure = token.replace("Bearer ", "");
	String cliente = provider.getNombreUsuarioFromToken(tokenPure);
	venta.setCliente(usuarioRepo.findByNombreUsuario(cliente).get());
	
	
	return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.save(venta));
    }
    
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
	return ResponseEntity.status(HttpStatus.OK).body(ventaService.findAll().get());
    }
    @GetMapping("/findAll/week/{fecha}")
    public ResponseEntity<?> findAllWeek(@PathVariable(name = "fecha") String fecha) throws ParseException{
	return ResponseEntity.status(HttpStatus.OK).body(ventaService.findAllByWeek(fecha).get());
    }
    
    @GetMapping("/findAll/mounth/{fecha}")
    public ResponseEntity<?> findAllMounth(@PathVariable(name = "fecha") String fecha) throws ParseException{
	return ResponseEntity.status(HttpStatus.OK).body(ventaService.findAllByMounth(fecha).get());
    }
    
    @GetMapping("/masVendidos/{fecha}/cant/{cant}")
    public ResponseEntity<?> findMoreFiveSales(@PathVariable(name = "fecha") String fecha,
	    @PathVariable(name="cant") Integer cant) throws ParseException{
	return ResponseEntity.status(HttpStatus.OK).body(ventaService.findMasVend(fecha,cant).get());
    }
    
    
    
}
