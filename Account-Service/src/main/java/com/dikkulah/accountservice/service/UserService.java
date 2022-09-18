package com.dikkulah.accountservice.service;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public Optional<User> get(UUID id) {
        return repository.findById(id);
    }
    public User save(User entity) {
        entity.setHashedPassword(passwordEncoder.encode(entity.getHashedPassword()));
        return repository.save(entity);
    }
    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getHashedPassword(), authorities);
        }
    }


}
