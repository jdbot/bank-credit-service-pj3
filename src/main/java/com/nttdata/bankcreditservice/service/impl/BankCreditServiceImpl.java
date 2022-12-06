package com.nttdata.bankcreditservice.service.impl;

import com.nttdata.bankcreditservice.document.BankCredit;
import com.nttdata.bankcreditservice.document.BankDebt;
import com.nttdata.bankcreditservice.document.Transaction;
import com.nttdata.bankcreditservice.dto.TransactionDto;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        bankCredit.setCreationDate(LocalDate.now().format(formatter));
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
    public Mono<BankCredit> payCredit(TransactionDto transaction) {
        return findById(transaction.getAccountId()).flatMap(x -> {
            float newAmount = x.getAmount() + transaction.getAmount();
            if (newAmount <= x.getCredit()){
                Transaction t = new Transaction(LocalDate.now().toString(),transaction.getAmount(),"credit payment",x.getCustomerId(), transaction.getAccountId(), newAmount);
                x.setAmount(newAmount);
                return this.webClient.build().post().uri("/transaction/").
                        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).
                        body(Mono.just(t), Transaction.class).
                        retrieve().
                        bodyToMono(Transaction.class).
                        flatMap(y -> update(x));
            }else{
                return Mono.empty();
            }
        });
    }

    @Override
    public Mono<BankCredit> chargeCredit(TransactionDto transaction) {
        return findById(transaction.getAccountId()).flatMap(x -> {
            float newAmount = x.getAmount() - transaction.getAmount();
            if (newAmount >= 0){
                Transaction t = new Transaction(LocalDate.now().toString(),transaction.getAmount(),"credit charge",x.getCustomerId(), transaction.getAccountId(), newAmount);
                x.setAmount(newAmount);
                return this.webClient.build().post().uri("/transaction/").
                        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).
                        body(Mono.just(t), Transaction.class).
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

    @Override
    public Mono<BankCredit> findByNumberCredit(String numberCredit) {
        return this.bankCreditRepository.findByNumberCredit(numberCredit);
    }


}
