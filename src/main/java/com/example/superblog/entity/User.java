package com.example.superblog.entity;

import lombok.Getter;

import javax.persistence.*;
/**
 * @author cyc
 */

@Entity
@Table(name = "user", schema = "superblog")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String username;

    @Column(name = "password")
    private String password;
}
