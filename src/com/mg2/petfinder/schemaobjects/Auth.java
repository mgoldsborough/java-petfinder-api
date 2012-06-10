package com.mg2.petfinder.schemaobjects;

import org.simpleframework.xml.Element;

public class Auth {
    @Element
    String key;

    @Element
    String token;

    @Element
    String expires;

    @Element
    String expiresString;

    public String getAuthToken() {
	return this.token;
    }

    public String getDateExpiresString() {
	return this.expiresString;
    }
}
