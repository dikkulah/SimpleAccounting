package com.dikkulah.accountservice.service;

import com.dikkulah.accountservice.exception.AccountNotFoundException;
import com.dikkulah.accountservice.exception.UserNotFoundException;
import com.dikkulah.accountservice.model.Account;
import com.dikkulah.accountservice.model.Activity;
import com.dikkulah.accountservice.model.User;
import com.dikkulah.accountservice.model.enums.ActivityType;
import com.dikkulah.accountservice.repository.AccountRepository;
import com.dikkulah.accountservice.repository.ActivitiesRepository;
import com.dikkulah.accountservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceTest {
    @InjectMocks
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ActivitiesRepository activitiesRepository;
    @Mock
    ModelMapper modelMapper;
    @Test
    void cant_add_activity_because_user_not_found() {
        var username = "ufuk";
        var amount = BigDecimal.ONE;
        var activity = new Activity();
        var user = new User();
        user.setUsername(username);
        var uuid = UUID.randomUUID();
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty()).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> accountService.addActivity(username,activity,uuid));
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,never()).findById(uuid);


    }
    @Test
    void cant_add_activity_because_account_not_found() {
        var username = "ufuk";
        var amount = BigDecimal.ONE;
        var activity = new Activity();
        var user = new User();
        user.setUsername(username);
        var uuid = UUID.randomUUID();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenThrow(AccountNotFoundException.class);
        assertThrows(AccountNotFoundException.class, () -> accountService.addActivity(username,activity,uuid));

        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);


    }
    @Test
    void cant_add_activity_because_account_is_not_belong_user() {
        var username = "ufuk";
        var uuid = UUID.randomUUID();
        var amount = BigDecimal.ONE;
        var user = new User();
        var activity = new Activity();
        user.setUsername(username);
        var account = new Account();
        account.setAmount(BigDecimal.TEN);
        account.setId(uuid);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        assertEquals(accountService.addActivity(username,activity,uuid),Boolean.FALSE);
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);

    }
    @Test
    void add_activity_sell() {
        var username = "ufuk";
        var uuid = UUID.randomUUID();
        var amount = BigDecimal.ONE;
        var user = new User();
        var activity = new Activity();
        var account = new Account();
        activity.setAmount(BigDecimal.ONE);
        account.setId(uuid);
        user.setId(uuid);
        user.setUsername(username);
        user.getAccounts().add(account);
        account.setAmount(BigDecimal.TEN);
        activity.setActivityType(ActivityType.SELL);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        when(activitiesRepository.save(activity)).thenReturn(activity);
        assertEquals(accountService.addActivity(username,activity,uuid),Boolean.TRUE);
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);

    }
    @Test
    void add_activity_buy() {
        var username = "ufuk";
        var uuid = UUID.randomUUID();
        var amount = BigDecimal.ONE;
        var user = new User();
        var activity = new Activity();
        var account = new Account();
        activity.setAmount(BigDecimal.ONE);
        account.setId(uuid);
        user.setId(uuid);
        user.setUsername(username);
        user.getAccounts().add(account);
        account.setAmount(BigDecimal.TEN);
        activity.setActivityType(ActivityType.BUY);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        when(activitiesRepository.save(activity)).thenReturn(activity);
        assertEquals(accountService.addActivity(username,activity,uuid),Boolean.TRUE);
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);

    }
    @Test
    void cant_check_balance_because_user_not_found() {
        var username = "ufuk";
        var amount = BigDecimal.ONE;
        var user = new User();
        user.setUsername(username);
        var uuid = UUID.randomUUID();
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty()).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> accountService.checkBalance(username,amount,uuid));
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,never()).findById(uuid);


    }

    @Test
    void cant_check_balance_because_account_not_found() {
        var username = "ufuk";
        var amount = BigDecimal.ONE;
        var user = new User();
        user.setUsername(username);
        var uuid = UUID.randomUUID();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenThrow(AccountNotFoundException.class);
        assertThrows(AccountNotFoundException.class, () -> accountService.checkBalance(username,amount,uuid));
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);
    }
    @Test
    void cant_check_balance_because_account_is_not_belong_user() {
        var username = "ufuk";
        var uuid = UUID.randomUUID();
        var amount = BigDecimal.ONE;
        var user = new User();
        user.setUsername(username);
        var account = new Account();
        account.setAmount(BigDecimal.TEN);
        account.setUser(user);
        account.setId(uuid);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        assertEquals(accountService.checkBalance(username,amount,uuid),Boolean.FALSE);
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);

    }
    @Test
    void cant_check_balance_because_account_dont_have_enough_money() {
        var username = "ufuk";
        var uuid = UUID.randomUUID();
        var amount = BigDecimal.TEN;
        var user = new User();
        user.setUsername(username);
        var account = new Account();
        account.setAmount(BigDecimal.ONE);
        account.setUser(user);
        account.setId(uuid);
        user.getAccounts().add(account);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        assertEquals(accountService.checkBalance(username,amount,uuid),Boolean.FALSE);
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);

    }
    @Test
    void check_balance_and_return_true() {
        var username = "ufuk";
        var uuid = UUID.randomUUID();
        var amount = BigDecimal.ONE;
        var user = new User();
        user.setUsername(username);
        var account = new Account();
        account.setAmount(BigDecimal.TEN);
        account.setUser(user);
        account.setId(uuid);
        user.getAccounts().add(account);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        assertEquals(accountService.checkBalance(username,amount,uuid),Boolean.TRUE);
        verify(userRepository,times(1)).findByUsername(username);
        verify(accountRepository,times(1)).findById(uuid);

    }

}