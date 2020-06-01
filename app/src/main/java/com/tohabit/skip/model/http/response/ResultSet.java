package com.tohabit.skip.model.http.response;

/**
 * Created by sundongdong on 2016/10/23.
 */

public class ResultSet<T> {

    public Integer code;
    public String msg;
    public T data;

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
