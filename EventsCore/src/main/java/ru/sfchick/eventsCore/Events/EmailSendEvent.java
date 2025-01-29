package ru.sfchick.eventsCore.Events;

public class EmailSendEvent {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EmailSendEvent(String content) {
        this.content = content;
    }

    public EmailSendEvent() {
    }

    @Override
    public String toString() {
        return "EmailSendEvent{" +
                "content='" + content + '\'' +
                '}';
    }
}
