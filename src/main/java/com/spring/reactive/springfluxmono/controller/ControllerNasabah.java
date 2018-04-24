package com.spring.reactive.springfluxmono.controller;

import com.spring.reactive.springfluxmono.entity.Nasabah;
import com.spring.reactive.springfluxmono.repository.ReactiveRepositoryNasabah;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/reactive/api/nasabah")
public class ControllerNasabah {

    @Autowired
    private ReactiveRepositoryNasabah reactiveRepositoryNasabah;

    @GetMapping
    public Flux<Nasabah> listNasabah(){
        return reactiveRepositoryNasabah.findAll();
    }

    /**
    @PostMapping(value = "/created")
    public Mono<Nasabah> createdNasabah(@RequestBody @Valid Nasabah nasabah){
        return reactiveRepositoryNasabah.save(nasabah);
    }
     **/

    @PostMapping(value = "/created")
    public Mono<ResponseEntity<Nasabah>> createdNasabah(@RequestBody @Valid Nasabah nasabah){
        return reactiveRepositoryNasabah.save(nasabah)
                .map(callbackJSON -> new ResponseEntity<Nasabah>(callbackJSON, HttpStatus.CREATED))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/{idnasabah}")
    public Mono<ResponseEntity<Nasabah>> findByIdnasabah(@PathVariable String idnasabah){
        return reactiveRepositoryNasabah.findById(idnasabah)
                .map(callbackJSON -> new ResponseEntity<Nasabah>(callbackJSON, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/updated/{idnasabah}")
    public Mono<ResponseEntity<Nasabah>> updatedNasabah(@PathVariable String idnasabah,
                                                        @Valid @RequestBody Nasabah nasabah){
        return reactiveRepositoryNasabah.findById(idnasabah)
                .flatMap(callbackJSON -> {
                    callbackJSON.setAlamat(nasabah.getAlamat());
                    callbackJSON.setCreatedate(nasabah.getCreatedate());
                    callbackJSON.setNotelp(nasabah.getNotelp());
                    callbackJSON.setNama(nasabah.getNama());
                    return reactiveRepositoryNasabah.save(callbackJSON);
                })
                .map(editedNasabah
                -> new ResponseEntity<Nasabah>(editedNasabah, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<Nasabah>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/deleted/{idnasabah}")
    public Mono<ResponseEntity<Void>> deletedNasabah(@PathVariable String idnasabah){

        return reactiveRepositoryNasabah.findById(idnasabah)
                .flatMap(callbackJSON ->
                reactiveRepositoryNasabah.delete(callbackJSON)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/stream/nasabah", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Nasabah> streamNasabah(){
        return reactiveRepositoryNasabah.findAll();
    }
}
