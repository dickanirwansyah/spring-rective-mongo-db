package com.spring.reactive.springfluxmono.repository;

import com.spring.reactive.springfluxmono.entity.Nasabah;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveRepositoryNasabah extends ReactiveMongoRepository<Nasabah, String>{
}
