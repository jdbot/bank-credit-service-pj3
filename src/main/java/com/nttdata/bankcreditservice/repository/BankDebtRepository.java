package com.nttdata.bankcreditservice.repository;

import com.nttdata.bankcreditservice.document.BankDebt;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Debt Repository.
 */
@Repository
public interface BankDebtRepository extends ReactiveMongoRepository<BankDebt, String> {
    Flux<BankDebt> findByCustomerId(String customerId);

    Mono<BankDebt> findByCreditId(String creditId);

}
