package com.mg2.petfinder.responseobjects;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import com.mg2.petfinder.schemaobjects.Pet;

@Root(name = "petfinder")
@Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
public class GetPetResponse extends BaseResponse {

    @Element(name = "pet", required = false)
    Pet pet;

    public Pet getPet() {
	return this.pet;
    }
}
