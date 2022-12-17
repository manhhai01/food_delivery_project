package com.cybersoft.food_project.exception;

public class DeviceZeroException extends RuntimeException {

    public DeviceZeroException() {
    }

    public DeviceZeroException(String message) {
        super(message);
    }
}
