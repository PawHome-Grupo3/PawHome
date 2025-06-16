package com.grupo3.pawHome.config;

import com.grupo3.pawHome.entities.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class MyUserDetails implements UserDetails {

    private final Usuario usuario;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean active;

    public MyUserDetails(Usuario usuario) {
        this.usuario = usuario;
        this.username = usuario.getNickname();
        this.password = usuario.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()));
        this.active = true; // Puedes usar algún campo tipo "activo" si lo implementas
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getNickname(); // o getEmail() si usas email como identificador
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Puedes cambiar esto si implementas lógica de expiración
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambia si quieres bloquear cuentas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true; // Puedes usar un campo booleano "activo" en tu entidad si lo deseas
    }
}