package com.mg2.petfinder.schemaobjects.types;

public enum SmallFurryType implements IBreedType {
	// Small furry types
	CHINCHILLA_RODENT("Chinchilla", "Chinchilla"),
	DEGU("Degu", "Degu"),
	FERRET("Ferret", "Ferret"),
	GERBIL("Gerbil", "Gerbil"),
	GUINEA_PIG("Guinea Pig", "Guinea%20Pig"),
	HAMSTER("Hamster", "Hamster"),
	HEDGEHOG("Hedgehog", "Hedgehog"),
	MOUSE("Mouse", "Mouse"),
	PRAIRIE_DOG("Prairie Dog", "Prairie%20Dog"),
	RAT("Rat", "Rat"),
	SKUNK("Skunk", "Skunk"),
	SUGAR_GLIDER("Sugar Glider", "Sugar%20Glider"),
	TARANTULA("Tarantula", "Tarantula");

	String name;
	String value;

	SmallFurryType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return this.value;
	}

	public String getValue() {
		return this.name;
	}
}
