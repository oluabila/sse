package com.universitysys.SSE.model;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;


@Entity // This tells Hibernate to make a table out of this class
@Configuration
@Table(name = "student")
public class Students {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    public String date_of_birth;
    @NotBlank
    public String nationality;
    @NotBlank
    public String sex;

    public Students() {

    }

    public Students(Integer id, String name, String surname) {
        this.id= id;
        this.name = name;
        this.surname = surname;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
            this.surname = surname;
    }
    public String getDate_of_birth() {return date_of_birth;}

    public void setDate_of_birth(String date_of_birth) {this.date_of_birth = date_of_birth;}

    public String getNationality(){return nationality;}

    public void setNationality(String nationality){this.nationality = nationality;}

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

