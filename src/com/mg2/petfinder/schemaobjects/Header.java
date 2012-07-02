package com.mg2.petfinder.schemaobjects;

import com.mg2.petfinder.PetfinderApiResponseCode;
import com.mg2.petfinder.exceptions.InvalidResponseCodeException;
import com.mg2.petfinder.wrappers.StringWrapper;

public class Header {

    private StringWrapper timestamp;
    private StringWrapper message;
    private Status status;

    public Header() {

    }

    public Status getStatus() {
	return status;
    }

    public String getTimestamp() {
	return timestamp.toString();
    }

    public String getMessage() {
	return message.toString();
    }

    public void validate() throws InvalidResponseCodeException {
	if (status == null)
	    throw new InvalidResponseCodeException(-1, status.getMessage());
	if (status.getCode() != PetfinderApiResponseCode.PFAPI_OK
		.getResponseCode())
	    throw new InvalidResponseCodeException(status.getCode(),
		    status.getMessage());
    }

    @Override
    public String toString() {
	return "Header [timestamp=" + timestamp + ", message=" + message
		+ ", status=" + status + "]";
    }
}
