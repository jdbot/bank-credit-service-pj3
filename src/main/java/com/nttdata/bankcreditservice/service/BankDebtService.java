package com.nttdata.bankcreditservice.service;

import com.nttdata.bankcreditservice.document.BankCredit;
import com.nttdata.bankcreditservice.document.BankDebt;
import com.nttdata.bankcreditservice.document.Transaction;
import com.nttdata.bankcreditservice.dto.TransactionDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Debt Service.
 */
public interface BankDebtService {

    Flux<BankDebt> findAll();

    Mono<BankDebt> register(BankDebt bankDebt);

    Mono<BankDebt> update(BankDebt bankDebt);

    Mono<BankDebt> findById(String id);

    Mono<Void> delete(String id);

    Mono<Boolean> existsById(String id);

    Flux<BankDebt> findByCustomerId(String customerId);

    Mono<BankDebt> payDebt(TransactionDto transaction);
}
