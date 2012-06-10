package com.mg2.petfinder;

public enum PetfinderApiMethod {
	AUTHENTICATE("auth.getToken?"),
	GET_PET("pet.get?"),
	GET_PET_RANDOM("pet.getRandom?"),
	FIND_PET("pet.find?"),	
	GET_BREED_LIST("breed.list?"),
	GET_SHELTER("shelter.get?"),
	GET_SHELTER_PETS("shelter.getPets?"), 
	GET_SHELTER_LIST_BY_BREED("shelter.listByBreed?"),
	GET_SHELTER_LIST("shelter.find?");
	
	String value;
	
	private PetfinderApiMethod(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
