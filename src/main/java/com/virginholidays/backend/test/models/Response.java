package com.virginholidays.backend.test.models;

public class Response<T> {
    private String message;
    private int code;
    private boolean status;
    private T data;

    public Response(int code, String message, T data) {
        this.code = code;
        this.status = code >= 200 && code < 300;
        this.message = message;
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
