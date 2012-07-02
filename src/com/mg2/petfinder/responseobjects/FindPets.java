package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.schemaobjects.Pet;
import com.mg2.petfinder.schemaobjects.Pets;
import com.mg2.petfinder.wrappers.IntWrapper;

public class FindPets<T extends Pet> extends BaseResponse {

    private IntWrapper lastOffset;

    private Pets<T> pets;

    public T[] getPets() {
	return pets.getPets();
    }

    public int getLastOffset() {
	return lastOffset.getInt();
    }

}
