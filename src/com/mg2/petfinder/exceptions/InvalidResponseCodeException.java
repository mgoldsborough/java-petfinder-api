package com.mg2.petfinder.exceptions;

public class InvalidResponseCodeException extends Exception {

    int responseCode;

    public InvalidResponseCodeException(int code, String message) {
	super(message);
	this.responseCode = code;
    }

    public InvalidResponseCodeException(int code) {
	super("Invalid Response Code (Header might be null)");
	this.responseCode = code;
    }

    public InvalidResponseCodeException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

    public int getResponseCode() {
	return responseCode;
    }

}
