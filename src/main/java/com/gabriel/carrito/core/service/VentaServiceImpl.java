package com.gabriel.carrito.core.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabriel.carrito.core.document.Venta;
import com.gabriel.carrito.core.dto.VentaItem;
import com.gabriel.carrito.core.dto.VentaLibroInd;
import com.gabriel.carrito.core.repository.LibroRepo;
import com.gabriel.carrito.core.repository.VentaRepo;

@Service
public class VentaServiceImpl {

    @Autowired
    private VentaRepo ventaRepo;
    @Autowired
    private Utilities utilities;

    @Autowired
    private LibroRepo libroRepo;

    public Optional<Venta> save(Venta venta) {
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	Date fechaActual = new Date();

	String codigoVenta = "";
	do {
	    codigoVenta = utilities.randomString(6, 4);
	} while (ventaRepo.existsByCodigoVenta(codigoVenta));
	venta.setCodigoVenta(codigoVenta);

	venta.setFechaVenta(sdformat.format(fechaActual));

	return Optional.of(ventaRepo.save(venta));
    }

    public Optional<Venta> findByCodigoVenta(String codigo) {
	return ventaRepo.findByCodigoVenta(codigo);
    }

    public Optional<List<Venta>> findAllByWeek(String inicioSemana) throws ParseException {
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	Date inicioSem = sdformat.parse(inicioSemana);
	Date inicioLapso = utilities.sumarRestarDias(inicioSem, -1);
	Date finLapso = utilities.sumarRestarDias(inicioLapso, 8);

	List<Venta> ventas = ventaRepo.findAll();
	List<Venta> ventasFiltradas = new ArrayList<Venta>();
	for (Venta ite : ventas) {
	    Date fecha = sdformat.parse(ite.getFechaVenta());
	    if ((fecha.compareTo(inicioLapso)) > 0 && (finLapso.compareTo(fecha) > 0)) {
		ventasFiltradas.add(ite);
	    }
	}

	return Optional.of(ventasFiltradas);

    }

    public Optional<List<Venta>> findAllByMounth(String inicioMes) throws ParseException {
	SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	Date inicioSem = sdformat.parse(inicioMes);
	Date inicioLapso = utilities.sumarRestarDias(inicioSem, -1);
	Date finLapso = utilities.sumarRestarMeses(inicioLapso, 1);

	List<Venta> ventas = ventaRepo.findAll();
	List<Venta> ventasFiltradas = new ArrayList<Venta>();
	for (Venta ite : ventas) {
	    Date fecha = sdformat.parse(ite.getFechaVenta());
	    if ((fecha.compareTo(inicioLapso)) > 0 && (finLapso.compareTo(fecha) > 0)) {
		ventasFiltradas.add(ite);
	    }
	}

	return Optional.of(ventasFiltradas);

    }

    public Optional<List<VentaLibroInd>> findMasVend(String inicioMes, Integer cant) throws ParseException {
	SortedMap<String, Integer> masVend = new TreeMap<String, Integer>(java.util.Collections.reverseOrder());

	// List<Venta> ventas = findAllByMounth(inicioMes).get();
	List<Venta> ventas = findAll().get();

	for (Venta venta : ventas) {
	    for (VentaItem ventaItem : venta.getLibros()) {
		// si el libro ya esta en la lista sima la cantidad
		if (masVend.containsKey(ventaItem.getLibro().getIsbn())) {
		    masVend.replace(ventaItem.getLibro().getIsbn(),
			    masVend.get(ventaItem.getLibro().getIsbn()) + ventaItem.getCantidad());
		} else {
		    masVend.put(ventaItem.getLibro().getIsbn(), ventaItem.getCantidad());
		}

	    }
	}

	List<VentaLibroInd> librosMasV = new ArrayList<>();

	Integer rep = 0;
	for (Map.Entry<String, Integer> entry : masVend.entrySet()) {
	    rep++;
	    VentaLibroInd vent = new VentaLibroInd(libroRepo.findByIsbn(entry.getKey()).get(), entry.getValue());
	    librosMasV.add(vent);
	    if(rep == cant)
		break;
	}

	return Optional.of(librosMasV);

    }

    public Optional<List<Venta>> findAll() {
	return Optional.of(ventaRepo.findAll());
    }

    public Optional<Venta> update(Venta venta) {
	return Optional.of(ventaRepo.save(venta));
    }

    public boolean delete(String codigo) {
	Optional<Venta> vent = ventaRepo.findByCodigoVenta(codigo);
	if (vent.isPresent()) {
	    ventaRepo.delete(vent.get());
	    return true;
	} else {
	    return false;
	}
    }

}
