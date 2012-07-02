package com.mg2.petfinder.responseobjects;

import com.mg2.petfinder.exceptions.InvalidResponseCodeException;

public class PetfinderResponse<T extends BaseResponse> {

    private T petfinder;

    public PetfinderResponse() {

    }

    public T getPetfinder() {
	return petfinder;
    }

    public void validate() throws InvalidResponseCodeException {
	petfinder.validate();
    }

    @Override
    public String toString() {
	return "GetPetResponse [petfinder=" + petfinder + "]";
    }

}
