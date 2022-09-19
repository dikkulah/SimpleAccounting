package com.dikkulah.accountservice.service;

import com.dikkulah.accountservice.exception.UserNotFoundException;
import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.Activity;
import com.dikkulah.accountservice.repository.AccountRepository;
import com.dikkulah.accountservice.repository.ActivitiesRepository;
import com.dikkulah.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ActivitiesRepository activitiesRepository;

    public List<Account> findAllAccountsByUsername(String name) {
        return accountRepository.findAccountsByUser_Username(name);
    }



    public Account addAccount(String name, Account account) {
        checkUser(name);
        return accountRepository.save(account);
    }

    public List<Activity> findAccountActivities(String name, UUID uuid) {
        checkUser(name);
        return activitiesRepository.findActivitiesByAccount_Id(uuid);
    }


    private void checkUser(String name) {
        userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
    }
}
