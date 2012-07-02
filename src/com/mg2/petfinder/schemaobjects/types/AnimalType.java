package com.mg2.petfinder.schemaobjects.types;


public enum AnimalType {
    HINT("Animal (Optional)", "", null),
    DOG("Dog", DogType.values()),
    CAT("Cat", CatType.values()),
    SMALL_FURRY("Small & Furry", "smallfurry", SmallFurryType.values()), 
    BARN_YARD("BarnYard", BarnYardType.values()),
    BIRD("Bird", BirdType.values()), 
    HORSE("Horse", HorseType.values()),
    PIG("Pig", PigType.values()),
    RABBIT("Rabbit", RabbitType.values()),
    REPTILE("Reptile", ReptileType.values());
	
    String name;
    String value;
    IBreedType[] values;

    AnimalType(String name, String value, IBreedType[] values) {
	this.name = name;
	this.value = value;
	this.values = values;
    }

    AnimalType(String str, IBreedType[] values) {
	this.name = this.value = str;
	this.values = values;
    }

    public String getName() {
	return this.name;
    }

    public String getValue() {
	return this.value;
    }

    public IBreedType[] getValues() {
	return this.values;
    }
    
    @Override
    public String toString() {
	return this.name;
    }
}
