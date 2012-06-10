package com.mg2.petfinder.schemaobjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "contact")
public class Contact {
    @Element(required = false)
    String name;

    @Element(required = false)
    String address1;

    @Element(required = false)
    String address2;

    @Element(required = false)
    String city;

    @Element(required = false)
    String state;

    @Element(required = false)
    String zip;

    @Element(required = false)
    String phone;

    @Element(required = false)
    String fax;

    @Element(required = false)
    String email;

    public String getPhone() {
	return phone;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress1() {
	return address1;
    }

    public void setAddress1(String address1) {
	this.address1 = address1;
    }

    public String getAddress2() {
	return address2;
    }

    public void setAddress2(String address2) {
	this.address2 = address2;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();

	if (name != null)
	    sb.append(name + ", ");

	if (address1 != null)
	    sb.append(address1 + ", ");

	if (address2 != null)
	    sb.append(address2 + ", ");

	if (city != null)
	    sb.append(city + ", ");

	if (state != null)
	    sb.append(state + ", ");

	if (zip != null)
	    sb.append(zip + ", ");

	if (phone != null)
	    sb.append(phone + ", ");

	if (fax != null)
	    sb.append(fax + ", ");

	if (email != null)
	    sb.append(email + ", ");

	String contact = sb.toString();
	return contact.substring(0, contact.length() - 2);
    }
}
