package com.mg2.petfinder.responseobjects;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "petfinder")
@Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi")
public class GetBreedListResponse extends BaseResponse {

    @ElementList(name = "breeds", required = false)
    List<String> breeds;

    public List<String> getBreedList() {
	return this.breeds;
    }
}
