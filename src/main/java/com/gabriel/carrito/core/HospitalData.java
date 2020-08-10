package com.gabriel.carrito.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabriel.carrito.core.service.VentaServiceImpl;

@SpringBootApplication
public class HospitalData implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(HospitalData.class, args);
	}
	
	@Autowired
	VentaServiceImpl venta;
	
	@Override
	public void run(String... args) throws Exception {
	  
	}

}
