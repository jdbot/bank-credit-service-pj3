package com.nttdata.bankcreditservice.service.impl;

import com.nttdata.bankcreditservice.document.BankCredit;
import com.nttdata.bankcreditservice.document.BankDebt;
import com.nttdata.bankcreditservice.document.Transaction;
import com.nttdata.bankcreditservice.repository.BankDebtRepository;
import com.nttdata.bankcreditservice.service.BankDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Debt Service Implementation.
 */
@Service
public class BankDebtServiceImpl implements BankDebtService {

    @Autowired
    private BankDebtRepository bankDebtRepository;

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public Flux<BankDebt> findAll() {
        return this.bankDebtRepository.findAll();
    }

    @Override
    public Mono<BankDebt> register(BankDebt BankDebt) {
        return this.bankDebtRepository.save(BankDebt);
    }

    @Override
    public Mono<BankDebt> update(BankDebt BankDebt) {
        return this.bankDebtRepository.save(BankDebt);
    }

    @Override
    public Mono<BankDebt> findById(String id) {
        return this.bankDebtRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.bankDebtRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return this.bankDebtRepository.existsById(id);
    }

    @Override
    public Flux<BankDebt> findByCustomerId(String customerId) {
        return this.bankDebtRepository.findByCustomerId(
                customerId).filter(a-> a.getBalance()> 0);
    }

    @Override
    public Mono<BankDebt> payDebt(Transaction transaction) {
        return this.bankDebtRepository.findByCreditId(transaction.getIdAccount()).flatMap(x -> {
            float newAmount = x.getBalance() + transaction.getAmount();
            x.setBalance(newAmount);
            return update(x);
        });
    }

    @Override
    public Mono<BankDebt> chargeDebt(Transaction transaction) {
        return this.bankDebtRepository.findByCreditId(transaction.getIdAccount()).flatMap(x -> {
            float newAmount = x.getBalance() - transaction.getAmount();
            x.setBalance(newAmount);
            return update(x);
        });
    }

}
