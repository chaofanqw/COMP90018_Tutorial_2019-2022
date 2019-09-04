package com.example.androidthreadtest;

// Message class for implementing EventBus library
public class MessageEvent {

    public final String message;

    public MessageEvent(String message) {
        this.message = message;
    }
}
