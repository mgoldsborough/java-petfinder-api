package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Pet;

public class Petfinder extends BaseResponse {

    private Pet pet;

    public Petfinder() {

    }

    public Pet getPet() {
	return pet;
    }
}
