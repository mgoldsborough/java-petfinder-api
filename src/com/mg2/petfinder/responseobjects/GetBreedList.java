package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Breeds;

public class GetBreedList extends BaseResponse {
    private Breeds breeds;

    public Breeds getBreeds() {
	return breeds;
    }
}
