package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Pet;

public class GetPet<T extends Pet> extends BaseResponse {

    private T pet;

    public GetPet() {
	
    }

    public T getPet() {
	return pet;
    }
}
