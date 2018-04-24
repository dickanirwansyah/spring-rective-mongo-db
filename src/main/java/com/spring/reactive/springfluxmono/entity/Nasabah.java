package com.spring.reactive.springfluxmono.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Getter
@Setter
@Document(collection = "nasabah")
public class Nasabah {

    @Id
    private String idnasabah;

    @NotBlank
    private String nama;

    @NotBlank
    private String alamat;

    @NotBlank
    private String notelp;


    private Date createdate = new Date();
}
