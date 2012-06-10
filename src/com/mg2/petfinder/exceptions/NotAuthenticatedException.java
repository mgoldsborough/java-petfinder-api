package com.mg2.petfinder.exceptions;

public class NotAuthenticatedException extends Exception {

    public NotAuthenticatedException() {
	super("You must be authenticated to call this method");
	// TODO Auto-generated constructor stub
    }

    public NotAuthenticatedException(String message, Throwable cause) {
	super("You must be authenticated to call this method", cause);
	// TODO Auto-generated constructor stub
    }

    public NotAuthenticatedException(String message) {
	super("You must be authenticated to call this method");
	// TODO Auto-generated constructor stub
    }

    public NotAuthenticatedException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

}
