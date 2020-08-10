package com.gabriel.carrito.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utilities {
    
    public Date sumarRestarDias(Date fecha,int dias) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(fecha);
	calendar.add(Calendar.DAY_OF_YEAR,dias);
	return calendar.getTime();
    }
    
    public Date sumarRestarMeses(Date fecha,int meses) {
   	Calendar calendar = Calendar.getInstance();
   	calendar.setTime(fecha);
   	calendar.add(Calendar.MONTH,meses);
   	return calendar.getTime();
       }
    
    
    /**
     * Genera una cadena aleatoria [letras][numeros] del tamaño que elijamos
     * @param tamLetras
     * @param tamNum
     * @return
     */
    public String randomString(Integer tamLetras,Integer tamNum) {
	char[] letras = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	char[] numeros = "0123456789".toCharArray();
	
	StringBuilder sb = new StringBuilder(tamLetras);
	Random random = new Random();
	for (int i = 0; i < tamLetras; i++) {
	       char c = letras[random.nextInt(letras.length)];
	       sb.append(c);
	}
	String outputLetras = sb.toString();
	
	StringBuilder sb2 = new StringBuilder(tamNum);
	Random random2 = new Random();
	for (int i = 0; i < tamNum; i++) {
	       char c = numeros[random2.nextInt(numeros.length)];
	       sb2.append(c);
	}
	String outputNumeros = sb2.toString();
	
	return outputLetras.concat(outputNumeros);
    }

    
}
