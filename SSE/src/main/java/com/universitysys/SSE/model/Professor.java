package com.universitysys.SSE.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Professor {
    @Id
    private int id;
    private String name;
    private String surname;

    public void setName(String name) {
        this.name = name;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }



    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }
}
