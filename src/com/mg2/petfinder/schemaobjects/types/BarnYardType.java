package com.mg2.petfinder.schemaobjects.types;

public enum BarnYardType implements IBreedType {
	// Barn yard types
	ALPACA("Alpaca", "Alpaca"),
	COW("Cow", "Cow"),
	GOAT("Goat", "Goat"),
	LLAMA("Llama", "Llama"),
	PIG__FARM("Pig (Farm)", "Pig%20(Farm)"),
	SHEEP("Sheep", "Sheep");

	String name;
	String value;

	BarnYardType(String name, String value) {
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
