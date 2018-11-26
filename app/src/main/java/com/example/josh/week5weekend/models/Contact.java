package com.example.josh.week5weekend.models;

public class Contact {
    String name;
    String number;
    String address;

    public Contact() {
        this.name = null;
        this.number = null;
        this.address = null;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
