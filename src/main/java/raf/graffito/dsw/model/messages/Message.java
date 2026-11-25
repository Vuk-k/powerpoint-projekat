package raf.graffito.dsw.model.messages;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public abstract class Message {
    private String content;
    private ZonedDateTime timeStamp;

    public Message(String contnent) {
        this.content = contnent;
        timeStamp = ZonedDateTime.now();
    }
}
