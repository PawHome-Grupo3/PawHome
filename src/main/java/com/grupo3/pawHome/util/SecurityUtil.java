package com.grupo3.pawHome.util;

import com.grupo3.pawHome.entities.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public void updateAuthenticatedUser(Usuario usuarioActualizado) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                usuarioActualizado,
                auth.getCredentials(),
                auth.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
