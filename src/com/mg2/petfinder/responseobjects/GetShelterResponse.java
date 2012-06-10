package com.mg2.petfinder.responseobjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import com.mg2.petfinder.schemaobjects.Shelter;

@Root(name = "petfinder")
@Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
public class GetShelterResponse extends BaseResponse {

    @Element(required = false)
    Shelter shelter;

    public Shelter getShelter() {
	return this.shelter;
    }
}
