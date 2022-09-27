package com.dikkulah.accountservice.service;

import com.dikkulah.accountservice.dto.AccountDto;
import com.dikkulah.accountservice.dto.ActivityDto;
import com.dikkulah.accountservice.exception.AccountNotFoundException;
import com.dikkulah.accountservice.exception.UserNotFoundException;
import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.Activity;
import com.dikkulah.accountservice.model.User;
import com.dikkulah.accountservice.model.enums.ActivityType;
import com.dikkulah.accountservice.model.enums.Currency;
import com.dikkulah.accountservice.repository.AccountRepository;
import com.dikkulah.accountservice.repository.ActivitiesRepository;
import com.dikkulah.accountservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ActivitiesRepository activitiesRepository;
    private final ModelMapper modelMapper;

    public List<AccountDto> findAllAccountsByUsername(String name) {
        return accountRepository.findAccountsByUser_Username(name).stream().map(account -> modelMapper.map(account, AccountDto.class)).toList();
    }


    public AccountDto addAccount(String name, Currency currency) {
        User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
        Account account = new Account();
        account.setCurrency(currency);
        account.setUser(user);
        account.setAmount(BigDecimal.ZERO);
        return modelMapper.map(accountRepository.save(account), AccountDto.class);
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

    @Transactional(Transactional.TxType.REQUIRED)
    public Boolean addActivity(String name, Activity activity, UUID accountId) {
        try {
            User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
            Account foundAccount = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

            if (user.getAccounts().stream().anyMatch(account -> account.getId().compareTo(accountId) == 0)) {
                if (activity.getActivityType() == ActivityType.SELL) {
                    foundAccount.setAmount(foundAccount.getAmount().subtract(activity.getAmount()));
                } else if (activity.getActivityType() == ActivityType.BUY) {
                    foundAccount.setAmount(foundAccount.getAmount().add(activity.getAmount()));

                }
                activity.setAccount(foundAccount);
                activitiesRepository.save(activity);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    public Boolean checkBalance(String name, BigDecimal amount, UUID accountId) {
        try {
            User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);

            Account foundAccount = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
            if (foundAccount.getAmount().compareTo(amount) >= 0 &&
                    user.getAccounts().stream().anyMatch(account -> account.getId().compareTo(accountId) == 0))
                return Boolean.TRUE;
            else return Boolean.FALSE;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
