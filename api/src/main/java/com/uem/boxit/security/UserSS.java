package com.uem.boxit.security;

import com.uem.boxit.model.Usuario;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
public class UserSS implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.usuario = usuario;
        this.authorities = authorities;
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
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getEnable();
    }

    public String getNome() {
        return this.usuario.getNome();
    }
}
