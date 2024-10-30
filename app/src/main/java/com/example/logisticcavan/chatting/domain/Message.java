package com.example.logisticcavan.chatting.domain;

import java.util.List;

public class Message {

    private String senderId;
    private String text;



    public Message() {

    }
    public Message(String senderId,String text) {
        this.senderId = senderId;
        this.text = text;
    }


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
