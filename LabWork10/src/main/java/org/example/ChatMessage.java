package org.example;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class ChatMessage {
    @NonNull private String author;
    @NonNull private String message;
    private Date dateOfSending;

    public ChatMessage(String author, String message){
        this.author = author;
        this.message = message;
        this.dateOfSending = new Date();
    }
}
