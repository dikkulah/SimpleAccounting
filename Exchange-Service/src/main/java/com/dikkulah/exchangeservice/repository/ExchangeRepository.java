package com.dikkulah.exchangeservice.repository;

import com.dikkulah.exchangeservice.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, UUID> {

}
