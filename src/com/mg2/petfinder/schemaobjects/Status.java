package com.mg2.petfinder.schemaobjects;

import org.simpleframework.xml.Element;

public class Status {
    @Element
    private int code;

    @Element(required = false)
    private String message;

    public Status() {

    }

    public int getCode() {
	return this.code;
    }

    public String getMessage() {
	return message;
    }
}
