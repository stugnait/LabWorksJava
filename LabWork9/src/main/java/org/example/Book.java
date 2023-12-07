package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Data
public class Book implements Serializable {
    private int id;
    @NonNull private String type;
    @NonNull private String author;
    @NonNull private String name;
    @NonNull private Date dateWritten;
    @JsonIgnore
    private Date dateTaken = new Date();
    @JsonIgnore
    private Date dateReturned = new Date();
}
