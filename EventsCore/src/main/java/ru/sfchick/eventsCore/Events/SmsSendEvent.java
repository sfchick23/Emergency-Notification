package ru.sfchick.eventsCore.Events;

public class SmsSendEvent {

    private String content;

    public SmsSendEvent(String content) {
        this.content = content;
    }

    public SmsSendEvent() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SmsSendEvent{" +
                "content='" + content + '\'' +
                '}';
    }
}
