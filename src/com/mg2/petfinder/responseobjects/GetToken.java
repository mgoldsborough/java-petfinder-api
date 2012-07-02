package com.mg2.petfinder.responseobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mg2.petfinder.wrappers.StringWrapper;

public class GetToken extends BaseResponse {

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
	    "yyyy-MM-dd'T'HH:mm:ss");

    private Auth auth;

    public Auth getAuth() {
	return auth;
    }

    public class Auth {

	private StringWrapper expiresString;
	private StringWrapper token;
	private StringWrapper expires;
	private StringWrapper key;

	public Auth() {
	    super();
	}

	public String getToken() {
	    if (token == null)
		return "";
	    return token.toString();
	}

	public Date getTokenExpires() {
	    return new Date(Long.valueOf(expires.toString()) * 1000);
	}

	public String getExpires() {
	    return expires.toString();
	}

	public String getKey() {
	    return key.toString();
	}
    }
}