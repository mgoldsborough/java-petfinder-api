package com.mg2.petfinder.responseobjects;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import com.mg2.petfinder.schemaobjects.Pet;

@Root(name = "petfinder")
@Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
public class GetShelterPetsResponse extends BaseResponse {
    @Element
    int lastOffset;

    @ElementList(name = "pets", required = false)
    List<Pet> pets;

    public List<Pet> getPetList() {
	return pets;
    }
}
