package com.example;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic(optional = false)
    @Column (name = "first_name")
    private String first_name;

    @Basic(optional = false)
    @Column (name = "last_name")
    private String last_name;

    @Basic(optional = false)
    @Column (name = "age")
    private int age;

    @Basic(optional = false)
    @Column (name = "email", unique = true)
    private String email;

    @Basic(optional = false)
    @Column (name = "password")
    private String password;

    public User() {

    }

    public User(String first_name, String last_name, int age, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
