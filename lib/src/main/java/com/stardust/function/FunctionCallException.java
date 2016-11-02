package com.stardust.function;

/**
 * Created by Stardust on 2016/11/2.
 */
public class FunctionCallException extends RuntimeException {
    public FunctionCallException() {
        super();
    }

    public FunctionCallException(String message) {
        super(message);
    }

    public FunctionCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionCallException(Throwable cause) {
        super(cause);
    }
}
