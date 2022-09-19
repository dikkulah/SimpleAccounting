package com.dikkulah.accountservice.service;

import com.dikkulah.accountservice.dto.AccountDto;
import com.dikkulah.accountservice.exception.UserNotFoundException;
import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.Activity;
import com.dikkulah.accountservice.repository.AccountRepository;
import com.dikkulah.accountservice.repository.ActivitiesRepository;
import com.dikkulah.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ActivitiesRepository activitiesRepository;
    private final ModelMapper modelMapper;

    public List<AccountDto> findAllAccountsByUsername(String name) {
        return accountRepository.findAccountsByUser_Username(name).stream().map(account -> modelMapper.map(account, AccountDto.class)).toList();
    }


    public Account addAccount(String name, Account account) {
        checkUser(name);
        return accountRepository.save(account);
    }

    public Page<Activity> findAccountActivities(String name, UUID uuid, Integer count) {
        checkUser(name);
        Pageable firstPageWithFiveElements = PageRequest.of(0, count);
        return activitiesRepository.findActivitiesByAccount_IdOrderByTimeDesc(uuid, firstPageWithFiveElements);
    }


    private void checkUser(String name) {
        userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
    }
}
