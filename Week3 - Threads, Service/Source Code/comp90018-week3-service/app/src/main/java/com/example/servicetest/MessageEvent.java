package com.example.servicetest;

public class MessageEvent {
    public static int ACTIVITY = 1;
    public static int SERVICE = 2;

    public final int type;
    public final String message;

    public MessageEvent(int type, String message) {
        this.type = type;
        this.message = message;
    }
}
