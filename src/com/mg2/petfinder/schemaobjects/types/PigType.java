package com.mg2.petfinder.schemaobjects.types;

public enum PigType implements IBreedType {
	// PIG TYPES
	POT_BELLIED("Pot Bellied", "Pot%20Bellied"),
	VIETNAMESE_POT_BELLIED(
			"Vietnamese Pot Bellied",
			"Vietnamese%20Pot%20Bellied");

	String name;
	String value;

	PigType(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}
}
