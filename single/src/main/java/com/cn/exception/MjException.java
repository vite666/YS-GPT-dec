package com.cn.exception;

import lombok.Data;

/**
 * @BelongsProject: YS-GPT-dec
 * @BelongsPackage: com.cn.exception
 * @Author: 刘志威
 * @CreateTime: 2024-05-28  19:06
 * @Description:
 * @Version: 1.0
 */
@SuppressWarnings("all")
@Data
public class MjException extends RuntimeException{
    /**
     * The Message.
     */
    private String message;

    /**
     * The Code.
     */
    private Integer code;


    /**
     * Instantiates a new Dall exception.
     *
     * @param message the message
     * @param code    the code
     */
    public MjException(final String message, final Integer code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    /**
     * Instantiates a new Dall exception.
     *
     * @param message the message
     */
    public MjException(final String message) {
        super(message);
        this.message = message;
        this.code = 500;
    }
}
