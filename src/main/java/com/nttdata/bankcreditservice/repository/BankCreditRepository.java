package com.nttdata.bankcreditservice.repository;

import com.nttdata.bankcreditservice.document.BankCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Credit Repository.
 */
@Repository
public interface BankCreditRepository extends ReactiveMongoRepository<BankCredit, String> {

    Flux<BankCredit> findByCustomerId(String customerId);

    Mono<BankCredit> findByNumberCredit(String numberCredit);
}
