package com.dikkulah.accountservice.repository;

import com.dikkulah.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, UUID> {

    List<Account> findAccountsByUser_Username(String user_username);
}
