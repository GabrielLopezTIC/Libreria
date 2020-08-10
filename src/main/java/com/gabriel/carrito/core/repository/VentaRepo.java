package com.gabriel.carrito.core.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.carrito.core.document.Venta;
@Repository
public interface VentaRepo extends MongoRepository<Venta, String> {

    Optional<Venta> findByCodigoVenta(String codigo);
    boolean existsByCodigoVenta(String codigoVenta);
}
