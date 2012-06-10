package com.mg2.petfinder.schemaobjects.types;

public enum ReptileType implements IBreedType {
	// Reptile breeds
	FISH("Fish", "Fish"),
	FROG("Frog", "Frog"),
	GECKO("Gecko", "Gecko"),
	HERMIT_CRAB("Hermit Crab", "Hermit%20Crab"),
	IGUANA("Iguana", "Iguana"),
	LIZARD("Lizard", "Lizard"),
	SNAKE("Snake", "Snake"),
	TURTLE("Turtle", "Turtle");

	String name;
	String value;

	ReptileType(String name, String value) {
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
