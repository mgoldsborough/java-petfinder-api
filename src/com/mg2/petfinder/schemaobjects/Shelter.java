package com.mg2.petfinder.schemaobjects;

import org.simpleframework.xml.Element;

public class Shelter {
    @Element(required = false)
    public String id;

    @Element(required = false)
    public String name;

    @Element(required = false)
    public String address1;

    @Element(required = false)
    public String address2;

    @Element(required = false)
    public String city;

    @Element(required = false)
    public String state;

    @Element(required = false)
    public String zip;

    @Element(required = false)
    public String country;

    @Element(required = false)
    public String latitude;

    @Element(required = false)
    public String longitude;

    @Element(required = false)
    public String phone;

    @Element(required = false)
    public String fax;

    @Element(required = false)
    public String email;

    public String getShelter() {
	return this.email;
    }

    public String getName() {
	return this.name;
    }

    public String getState() {
	return this.state;
    }

    public String getCity() {
	return this.city;
    }

}
