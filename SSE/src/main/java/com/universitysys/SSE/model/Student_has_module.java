package com.universitysys.SSE.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Student_has_module {
    @Id
    private int grade;
    private int student_id;
    private int module_id;

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
