package org.example;

import java.util.Date;

public class LibraryManager extends Person {
    public LibraryManager(String fullName, Date dateOfBirth) {
        super(fullName, (java.sql.Date) dateOfBirth);
    }
}
