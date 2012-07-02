package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Pet;
import com.mg2.petfinder.schemaobjects.Pets;

public class GetShelterPets<T extends Pet> extends BaseResponse {

    private Pets<T> pets;

    public T[] getPets() {
	return pets.getPets();
    }
}
