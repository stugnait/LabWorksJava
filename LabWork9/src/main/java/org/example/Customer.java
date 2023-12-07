package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Customer extends Person implements Serializable {
    @JsonIgnore
    private int id;
    private ReaderTicket readerTicket;
    public Customer(String fullName, java.util.Date dateOfBirth) {
        super(fullName, dateOfBirth);
        readerTicket = new ReaderTicket(this);
    }
}

