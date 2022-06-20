package com.example.superblog.enums;

/**
 * @author cycsir
 */

public enum ResponseCode {
    // query ok
    OK(200),
    // query error
    ERROR(500);

    /**
     * 用private final修饰: 只能在当前类中访问;是常量，不能修改
     */
    private final Integer code;

    ResponseCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
