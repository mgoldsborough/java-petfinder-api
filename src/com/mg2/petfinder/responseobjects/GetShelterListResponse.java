package com.mg2.petfinder.responseobjects;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import com.mg2.petfinder.schemaobjects.Shelter;

@Root(name = "petfinder")
@Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
public class GetShelterListResponse extends BaseResponse {

    @ElementList(name = "shelters", required = false)
    List<Shelter> shelters;

    @Element(name = "lastOffset", required = false)
    int lastOffset;

    public List<Shelter> getShelterList() {
	return shelters;
    }

    public int getLastOffset() {
	return lastOffset;
    }
}
