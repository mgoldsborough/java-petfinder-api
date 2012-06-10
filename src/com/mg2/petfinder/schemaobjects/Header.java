package com.mg2.petfinder.schemaobjects;

import org.simpleframework.xml.Element;

import com.mg2.petfinder.PetfinderApiResponseCode;
import com.mg2.petfinder.exceptions.InvalidResponseCodeException;

public class Header {
    @Element
    String version;

    @Element
    public String timestamp;

    @Element
    public Status status;

    public Header() {

    }

    public void validate() throws InvalidResponseCodeException {
	if (status == null)
	    throw new InvalidResponseCodeException(-1, status.getMessage());
	if (status.getCode() != PetfinderApiResponseCode.PFAPI_OK
		.getResponseCode())
	    throw new InvalidResponseCodeException(status.getCode(),
		    status.getMessage());
    }
}
