package com.gabriel.carrito.core.security.jwt;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.gabriel.carrito.core.security.document.UsuarioPrincipal;

import java.util.Date;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuario = (UsuarioPrincipal) authentication.getPrincipal();
        
	String token = Jwts
			.builder()
			.setId("hdcJWT")
			.setSubject(usuario.getUsername())
			.claim("authorities",usuario.getAuthorities())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + (expiration * 60000))) //minutos
			.signWith(SignatureAlgorithm.HS512,
					secret.getBytes()).compact();
	return "Bearer " + token;
        
    }

    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("token expirado");
        }catch (IllegalArgumentException e){
            logger.error("token vacío");
        }catch (SignatureException e){
            logger.error("fail en la firma: "+e );
        }
        return false;
    }
}
