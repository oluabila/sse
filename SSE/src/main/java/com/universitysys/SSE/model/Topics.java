package com.universitysys.SSE.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Topics {
    @Id
    public int idtopics;
    public String title;
    public int grade_distribution;

    public int getGrade_distribution() {
        return grade_distribution;
    }

    public int getIdtopics() {
        return idtopics;
    }

    public String getTitle() {
        return title;
    }

    public void setGrade_distribution(int grade_distribution) {
        this.grade_distribution = grade_distribution;
    }

    public void setIdtopics(int idtopics) {
        this.idtopics = idtopics;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

