package com.example.logisticcavan.chatting.domain;

import java.util.List;

public class Messages {

   private List<Message> messages;


    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public  void  setMessage(Message message){
        this.messages.add(message);
    }
}
