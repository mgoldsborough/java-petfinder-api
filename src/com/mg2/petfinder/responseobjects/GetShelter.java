package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Shelter;

public class GetShelter extends BaseResponse {

    Shelter shelter;

    public Shelter getShelter() {
	return this.shelter;
    }
}
