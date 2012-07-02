package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Shelter;
import com.mg2.petfinder.schemaobjects.Shelters;

public class GetShelterList extends BaseResponse {

    private Shelters shelters;

    public Shelter[] getShelters() {
	return shelters.getShelters();
    }
}
