package com.pickemsystem.pickemsystembackend.email;

public interface EmailSender {
    void send(String subject, String to, String email);
}
