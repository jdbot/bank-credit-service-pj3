package com.nttdata.bankcreditservice.service.impl;

import com.nttdata.bankcreditservice.document.CreditType;
import com.nttdata.bankcreditservice.repository.CreditTypeRepository;
import com.nttdata.bankcreditservice.service.CreditTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Credit Type Service Implementation.
 */
@Service
public class CreditTypeServiceImpl implements CreditTypeService {

    @Autowired
    private CreditTypeRepository creditTypeRepository;

    @Override
    public Flux<CreditType> findAll() {
        return this.creditTypeRepository.findAll();
    }

    @Override
    public Mono<CreditType> register(CreditType creditType) {
        return this.creditTypeRepository.save(creditType);
    }

    @Override
    public Mono<CreditType> update(CreditType creditType) {
        return this.creditTypeRepository.save(creditType);
    }

    @Override
    public Mono<CreditType> findById(String id) {
        return this.creditTypeRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.creditTypeRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return this.creditTypeRepository.existsById(id);
    }
}
