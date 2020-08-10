package com.gabriel.carrito.core.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gabriel.carrito.core.security.document.Usuario;
import com.gabriel.carrito.core.security.document.UsuarioPrincipal;
import com.gabriel.carrito.core.security.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
	
        Optional<Usuario> usuario = usuarioRepo.findByNombreUsuario(nombreUsuario);
        
        if(usuario.isPresent()) {
            System.out.println("usuario encontrado");
            return UsuarioPrincipal.build(usuario.get());
        }else {
            System.out.println("usuario no encontrado");
            return null;
        }
    }
}
