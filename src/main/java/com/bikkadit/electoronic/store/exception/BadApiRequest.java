package com.bikkadit.electoronic.store.exception;

public class BadApiRequest extends  RuntimeException{


    private String message;

    public BadApiRequest(String message) {
        super(message);
        this.message = message;
    }
}
