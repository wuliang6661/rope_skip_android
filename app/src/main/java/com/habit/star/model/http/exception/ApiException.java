package com.habit.star.model.http.exception;


public class ApiException extends Exception {
    public int code = -1;
    public String message;
    public String data;

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public ApiException(int code, String msg, String data) {
        super(msg);
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ApiException(String msg) {
        super(msg);
    }
}
