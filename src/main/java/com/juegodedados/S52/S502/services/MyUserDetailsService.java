package com.juegodedados.S52.S502.services;

import com.juegodedados.S52.S502.models.PlayerModel;
import com.juegodedados.S52.S502.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PlayerModel> player = playerRepository.findByName(username);
        if (!player.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return new User(player.get().getName(), "", new ArrayList<>());
    }
}
