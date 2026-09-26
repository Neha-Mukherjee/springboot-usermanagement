package com.springboot.springboot_usermanagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    //@Column(name="first_name", nullable=false) - can be used to name the columns else jpa auto names it. used to configure constants

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    //default constructor
    public User(){

    }
    //getter setter
    public User(Long id , String email, String lastName, String firstName) {
        this.id = id;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getId() {
        return id;
    }

    public void setId(long Id) {
        this.id = id;
    }
}
