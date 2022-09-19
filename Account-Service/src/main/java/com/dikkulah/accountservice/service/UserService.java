package com.dikkulah.accountservice.service;

import com.dikkulah.accountservice.exception.UserNotFoundException;
import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.User;
import com.dikkulah.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Optional<User> get(UUID id) {
        return userRepository.findById(id);
    }
    public User save(User entity) {
        entity.setHashedPassword(passwordEncoder.encode(entity.getHashedPassword()));
        return userRepository.save(entity);
    }
    public User update(User entity) {
        return userRepository.save(entity);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public int count() {
        return (int) userRepository.count();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getHashedPassword(), authorities);
        }
    }



}
