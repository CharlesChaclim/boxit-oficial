package com.uem.boxit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uem.boxit.model.enums.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    private Boolean enable;
    private String telefone;
    private String confirmCode;
    private String passwordResetCode;
    private String nome;

    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordRestCodeExpiryDate;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    private Role role;
}