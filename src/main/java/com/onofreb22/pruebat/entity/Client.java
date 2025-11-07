package com.onofreb22.pruebat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="client")
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_client")
    private int id;

    @Column(name="fullName")
    private String fullName;

    public Client() {}

    public Client(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", fullName=" + fullName + "]"; 
    }
}
