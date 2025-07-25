package com.cefet.trab_republica.services;

import com.cefet.trab_republica.repositories.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private MoradorRepository moradorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Aqui estamos dizendo que o "username" para login é o campo de e-mail
        return moradorRepository.findByEmail(username);
    }
}
