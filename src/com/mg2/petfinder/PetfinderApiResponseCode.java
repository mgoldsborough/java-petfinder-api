package com.mg2.petfinder;

public enum PetfinderApiResponseCode {
	PFAPI_OK(100, "No Error"),
	PF_API_ERR_INVALID(200, "Invalid request"),
	PFAPI_ERR_NOENT(201, "Record does not exist"),
	PFAPI_ERR_LIMIT(202, "A limit as exceeded"),
	PFAPI_ERR_LOCATION(203, "Invalid geographical location"),
	PFAPI_ERR_UNAUTHORIZED(300, "Request is unauthorized"),
	PFAPI_ERR_AUTHFAIL(301, "Authentican failure"),
	PFAPI_ERR_INTERNAL(999, "Generic internal error");
	
	int responseCode;
	String msg;
	
	private PetfinderApiResponseCode(int code, String msg) {
		this.responseCode = code;
		this.msg = msg;
	}
	
	public int getResponseCode() {
		return this.responseCode;
	}
	
	@Override
	public String toString() {
		return this.msg;
	}
}
