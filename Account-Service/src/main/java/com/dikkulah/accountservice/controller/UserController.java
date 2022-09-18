package com.dikkulah.accountservice.controller;

import com.dikkulah.accountservice.model.User;
import com.dikkulah.accountservice.repository.UserRepository;
import com.dikkulah.accountservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("singUp")
    public ResponseEntity<User> singUp(@RequestBody User request) {
        log.info(request.toString());
        return new ResponseEntity<>(userService.save(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        Pageable firstPageWithFiveElements = PageRequest.of(0, 5);
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }


}
