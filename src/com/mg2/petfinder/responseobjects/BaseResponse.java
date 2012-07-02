package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.exceptions.InvalidResponseCodeException;
import com.mg2.petfinder.schemaobjects.Header;

public class BaseResponse {

    protected Header header;

    public void validate() throws InvalidResponseCodeException {
	if (header == null)
	    throw new InvalidResponseCodeException(header.getStatus().getCode());
	header.validate();
    }

    public Header getHeader() {
	return this.header;
    }
}
