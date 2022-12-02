package com.nttdata.bankcreditservice.service.impl;

import com.nttdata.bankcreditservice.document.BankCredit;
import com.nttdata.bankcreditservice.document.BankDebt;
import com.nttdata.bankcreditservice.document.Transaction;
import com.nttdata.bankcreditservice.repository.BankCreditRepository;
import com.nttdata.bankcreditservice.service.BankCreditService;
import com.nttdata.bankcreditservice.service.BankDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Credit Service Implementation.
 */
@Service
public class BankCreditServiceImpl implements BankCreditService {

    @Autowired
    private BankCreditRepository bankCreditRepository;

    @Autowired
    private BankDebtService bankDebtService;

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public Flux<BankCredit> findAll() {
        return this.bankCreditRepository.findAll();
    }

    @Override
    public Mono<BankCredit> register(BankCredit bankCredit) {
        return this.bankCreditRepository.save(bankCredit);
    }

    @Override
    public Mono<BankCredit> update(BankCredit bankCredit) {
        return this.bankCreditRepository.save(bankCredit);
    }

    @Override
    public Mono<BankCredit> findById(String id) {
        return this.bankCreditRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.bankCreditRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return this.bankCreditRepository.existsById(id);
    }

    @Override
    public Mono<BankCredit> payCredit(Transaction transaction) {
        return findById(transaction.getIdAccount()).flatMap(x -> {
            float newAmount = x.getAmount() + transaction.getAmount();
            if (newAmount <= x.getCredit()){
                transaction.setType("credit payment");
                transaction.setIdClient(x.getCustomerId());
                transaction.setAccountAmount(newAmount);
                x.setAmount(newAmount);
                return this.webClient.build().post().uri("/transaction/").
                        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).
                        body(Mono.just(transaction), Transaction.class).
                        retrieve().
                        bodyToMono(Transaction.class).
                        flatMap(y -> update(x));
            }else{
                return Mono.empty();
            }
        });
    }

    @Override
    public Mono<BankCredit> chargeCredit(Transaction transaction) {
        return findById(transaction.getIdAccount()).flatMap(x -> {
            float newAmount = x.getAmount() - transaction.getAmount();
            if (newAmount >= 0){
                transaction.setType("credit charge");
                transaction.setIdClient(x.getCustomerId());
                transaction.setAccountAmount(newAmount);
                x.setAmount(newAmount);
                return this.webClient.build().post().uri("/transaction/").
                        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).
                        body(Mono.just(transaction), Transaction.class).
                        retrieve().
                        bodyToMono(Transaction.class).
                        flatMap(y -> update(x));
            }else{
                return Mono.empty();
            }
        });
    }

    @Override
    public Flux<BankCredit> findByCustomerId(String customerId) {
        return this.bankCreditRepository.findByCustomerId(
                customerId);
    }

}
