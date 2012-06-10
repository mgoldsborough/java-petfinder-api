package com.mg2.petfinder.schemaobjects.types;

public enum HorseType implements IBreedType {
	// Horse types
	APPALOOSA("Appaloosa", "Appaloosa"),
	ARABIAN("Arabian", "Arabian"),
	BELGIAN("Belgian", "Belgian"),
	CLYDESDALE("Clydesdale", "Clydesdale"),
	CURLY_HORSE("Curly Horse", "Curly%20Horse"),
	DONKEY("Donkey", "Donkey"),
	MULE("Mule", "Mule"),
	DRAFT("Draft", "Draft"),
	GAITED("Gaited", "Gaited"),
	GRADE("Grade", "Grade"),
	LIPIZZAN("Lipizzan", "Lipizzan"),
	MINIATURE_HORSE("Miniature Horse", "Miniature%20Horse"),
	MISSOURI_FOXTROTTER("Missouri Foxtrotter", "Missouri%20Foxtrotter"),
	MORGAN("Morgan", "Morgan"),
	MUSTANG("Mustang", "Mustang"),
	PAINT_PINTO("Paint/Pinto", "Paint%2FPinto"),
	PALOMINO("Palomino", "Palomino"),
	PASO_FINO("Paso Fino", "Paso%20Fino"),
	PERCHERON("Percheron", "Percheron"),
	PERUVIAN_PASO("Peruvian Paso", "Peruvian%20Paso"),
	PONY("Pony", "Pony"),
	QUARTERHORSE("Quarterhorse", "Quarterhorse"),
	SADDLEBRED("Saddlebred", "Saddlebred"),
	SHETLAND_PONY("Shetland Pony", "Shetland%20Pony"),
	STANDARDBRED("Standardbred", "Standardbred"),
	TENNESSEE_WALKER("Tennessee Walker", "Tennessee%20Walker"),
	THOROUGHBRED("Thoroughbred", "Thoroughbred"),
	WARMBLOOD("Warmblood", "Warmblood");

	String name;
	String value;

	HorseType(String name, String value) {
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
