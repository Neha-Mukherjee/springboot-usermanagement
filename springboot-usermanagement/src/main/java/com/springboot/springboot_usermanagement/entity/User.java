package com.springboot.springboot_usermanagement.entity;

import jakarta.annotation.Nullable;
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
    @Column(nullable = false)
    private String password;

    //default constructor
    public User(){

    }
    //getter setter
    public User(Long id , String lastName, String firstName, String email,String password) {
        this.id = id;

        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password=password;

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

    public String getPassword(){return password;}

    public void  setPassword(String password){this.password=password;}
}
