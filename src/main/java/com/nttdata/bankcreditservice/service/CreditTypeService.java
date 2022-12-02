package com.nttdata.bankcreditservice.service;

import com.nttdata.bankcreditservice.document.CreditType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Credit Type Service.
 */
public interface CreditTypeService {

    Flux<CreditType> findAll();

    Mono<CreditType> register(CreditType creditType);

    Mono<CreditType> update(CreditType creditType);

    Mono<CreditType> findById(String id);

    Mono<Void> delete(String id);

    Mono<Boolean> existsById(String id);
}
