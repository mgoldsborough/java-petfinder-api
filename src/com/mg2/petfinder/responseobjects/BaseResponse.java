package com.mg2.petfinder.responseobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import com.mg2.petfinder.exceptions.InvalidResponseCodeException;
import com.mg2.petfinder.schemaobjects.Header;

public class BaseResponse {
    @SuppressWarnings("unused")
    @Attribute
    private String noNamespaceSchemaLocation;

    @Element(name = "header")
    protected Header header;

    public void validate() throws InvalidResponseCodeException {
	if (header == null)
	    throw new InvalidResponseCodeException(header.status.getCode());
	header.validate();
    }

    public Header getHeader() {
	return this.header;
    }
}
