package com.example.reportservice.shared.exception;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Message {
    private  String message;

    public Message(String message) {
        this.message = message;
    }
}
