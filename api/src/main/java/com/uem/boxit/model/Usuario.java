package com.uem.boxit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uem.boxit.model.enums.Role;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("U")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    @Email
    private String email;

    @JsonIgnore
    private String password;

    private Boolean enable;
    private String telefone;

    @JsonIgnore
    private String confirmCode;

    @JsonIgnore
    private String passwordResetCode;
    private String nome;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordRestCodeExpiryDate;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;
}