package com.dikkulah.accountservice.service;

import com.dikkulah.accountservice.dto.AccountDto;
import com.dikkulah.accountservice.dto.ActivityDto;
import com.dikkulah.accountservice.exception.UserNotFoundException;
import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.User;
import com.dikkulah.accountservice.model.enums.Currency;
import com.dikkulah.accountservice.repository.AccountRepository;
import com.dikkulah.accountservice.repository.ActivitiesRepository;
import com.dikkulah.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    public Account addAccount(String name, Currency currency) {
        User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
        Account account = new Account();
        account.setCurrency(currency);
        account.setUser(user);
        account.setAmount(BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    public List<ActivityDto> findAccountActivities(String name, UUID uuid, Integer count) {
        Pageable topX = PageRequest.of(0, count);
        checkUser(name);
        return activitiesRepository.findActivitiesByAccount_IdOrderByTimeDesc(uuid, topX).stream()
                .map(activity -> modelMapper.map(activity, ActivityDto.class)).toList();
    }


    private void checkUser(String name) {
        userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
    }
}
