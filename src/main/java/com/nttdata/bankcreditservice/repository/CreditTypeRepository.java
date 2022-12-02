package com.nttdata.bankcreditservice.repository;

import com.nttdata.bankcreditservice.document.CreditType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Credit Type Repository.
 */
@Repository
public interface CreditTypeRepository extends ReactiveMongoRepository<CreditType, String> {

}
