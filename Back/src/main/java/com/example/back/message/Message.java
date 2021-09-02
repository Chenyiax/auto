package com.example.back.message;

import net.bytebuddy.implementation.bind.annotation.Super;

public class Message {
    private int number;
    private String date;
    private String message;

    public Message(){

    };

    public Message(int number, String date, String message) {
        this.date = date;
        this.message = message;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Message1{" +
                "number=" + number +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
