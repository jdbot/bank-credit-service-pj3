package com.nttdata.bankcreditservice.controller;

import com.nttdata.bankcreditservice.document.BankDebt;
import com.nttdata.bankcreditservice.document.Transaction;
import com.nttdata.bankcreditservice.dto.TransactionDto;
import com.nttdata.bankcreditservice.service.BankDebtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller of Bank Debt.
 */
@RestController
@RequestMapping("/bankDebt")
public class BankDebtController {

    @Autowired
    private BankDebtService bankDebtService;

    //Method to get all the bank debt
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<BankDebt> findAll() {
        return bankDebtService.findAll();
    }

    //Method to insert a new bank debt
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BankDebt> register(@RequestBody BankDebt BankDebt) {
        return bankDebtService.register(BankDebt);
    }

    //Method to update a bank debt
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankDebt> update(@RequestBody BankDebt BankDebt) {
        return bankDebtService.update(BankDebt);
    }

    //Method to get a bank debt by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankDebt> findById(@PathVariable("id") String id) {
        return bankDebtService.findById(id);
    }

    //Method to delete a bank debt
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id) {
        return bankDebtService.delete(id);
    }

    //Method to get a bank debt by clientId
    @GetMapping("/debtByCustomerId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BankDebt> findByCustomerId(@PathVariable("id") String customerId) {
        return bankDebtService.findByCustomerId(customerId);
    }

    //Method to pay debt
    @PutMapping("/payDebt")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankDebt> payDebt(@RequestBody TransactionDto transaction) {
        return bankDebtService.payDebt(transaction);
    }

}
