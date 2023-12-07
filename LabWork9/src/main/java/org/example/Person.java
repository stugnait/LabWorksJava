package org.example;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class Person implements Serializable {
    private String fullName;
    private Date dateOfBirth;
    public Person(String fullName, Date dateOfBirth) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(){

    }
}
