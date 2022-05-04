package com.example.supportservice.shared.execption;
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
