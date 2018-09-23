package com.uem.boxit.security;

import com.uem.boxit.model.Usuario;
import com.uem.boxit.model.enums.Role;
import com.uem.boxit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> useropt = usuarioRepository.findByUsername(username);
        Usuario usuario = useropt.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorreto!"));
        return new UserSS(usuario, getAuthorities(usuario.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        return authorities;
    }
}
