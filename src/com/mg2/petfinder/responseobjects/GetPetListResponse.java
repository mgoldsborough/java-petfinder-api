package com.mg2.petfinder.responseobjects;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import com.mg2.petfinder.schemaobjects.Pet;

@Root(name = "petfinder")
@Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
public class GetPetListResponse extends BaseResponse {

    @ElementList(name = "pets", required = false)
    List<Pet> pets;

    @Element(name = "lastOffset", required = false)
    int lastOffset;

    public List<Pet> getPetList() {
	return pets;
    }

    public int getLastOffset() {
	return lastOffset;
    }
}
