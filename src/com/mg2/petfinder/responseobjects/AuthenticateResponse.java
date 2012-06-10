package com.mg2.petfinder.responseobjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import com.mg2.petfinder.schemaobjects.Auth;

@Root(name = "petfinder")
@Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
public class AuthenticateResponse extends BaseResponse {

    @Element
    private Auth auth;

    public AuthenticateResponse() {
	super();
    }

    public String getAuthToken() {
	return this.auth.getAuthToken();
    }

    public String getDateExpiresString() {
	return this.auth.getDateExpiresString();
    }
}
