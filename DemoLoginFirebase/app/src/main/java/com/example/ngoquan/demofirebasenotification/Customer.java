package com.example.ngoquan.demofirebasenotification;

import java.io.Serializable;

/**
 * Created by NGOQUAN on 1/23/2017.
 */
public class Customer implements Serializable {

    public String name;
    public String email;

    public Customer() {
    }

    public Customer(String name, String email) {
        this.email = email;
        this.name = name;
    }
}
