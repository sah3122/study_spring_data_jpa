package me.study.springdatajpa.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String firstName;

    private String lastName;
}
