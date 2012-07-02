package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Shelter;
import com.mg2.petfinder.schemaobjects.Shelters;

public class GetShelterListByBreed extends BaseResponse {

    private Shelters shelters;

    public Shelter[] getShelterList() {
	return shelters.getShelters();
    }
}
