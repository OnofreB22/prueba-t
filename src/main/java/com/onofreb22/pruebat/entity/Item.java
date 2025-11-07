package com.onofreb22.pruebat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_item")
    private int id;

    @Column(name="name")
    private String name;

    public Item() {}

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setFullName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + "]"; 
    }
}
