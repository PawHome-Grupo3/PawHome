package com.grupo3.pawHome.util;

import com.grupo3.pawHome.config.MyUserDetails;
import com.grupo3.pawHome.entities.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public void updateAuthenticatedUser(Usuario usuarioActualizado) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Envuelve el usuario actualizado en una nueva instancia de MyUserDetails
        MyUserDetails userDetailsActualizado = new MyUserDetails(usuarioActualizado);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                userDetailsActualizado,
                auth.getCredentials(),
                userDetailsActualizado.getAuthorities() // Usa las authorities del nuevo MyUserDetails
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
