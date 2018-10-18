package com.smart.tech.findfriend.domain;

import lombok.Data;

@Data
public class Result<T> {
    private boolean success = true;
    private String msg = "";
    private T data = null;

    public Result() {
        super();
    }

    public Result(boolean success) {
        super();
        this.success = success;
    }

    public Result(boolean success, T data) {
        super();
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String msg, T data) {
        super();
        this.success = success;
        this.msg = msg;
        this.data = data;
    }
}
