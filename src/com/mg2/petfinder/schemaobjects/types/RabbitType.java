package com.mg2.petfinder.schemaobjects.types;

public enum RabbitType implements IBreedType {
	// Rabbit types
	AMERICAN("American", "American"),
	AMERICAN_FUZZY_LOP("American Fuzzy Lop", "American%20Fuzzy%20Lop"),
	AMERICAN_SABLE("American Sable", "American%20Sable"),
	ANGORA_RABBIT("Angora Rabbit", "Angora%20Rabbit"),
	BELGIAN_HARE("Belgian Hare", "Belgian%20Hare"),
	BEVEREN("Beveren", "Beveren"),
	BRITANNIA_PETITE("Britannia Petite", "Britannia%20Petite"),
	BUNNY_RABBIT("Bunny Rabbit", "Bunny%20Rabbit"),
	CALIFORNIAN("Californian", "Californian"),
	CHAMPAGNE_D_ARGENT("Champagne D'Argent", "Champagne%20D'Argent"),
	CHECKERED_GIANT("Checkered Giant", "Checkered%20Giant"),
	CHINCHILLA("Chinchilla", "Chinchilla"),
	CINNAMON("Cinnamon", "Cinnamon"),
	CREME_D_ARGENT("Creme D'Argent", "Creme%20D'Argent"),
	DUTCH("Dutch", "Dutch"),
	DWARF("Dwarf", "Dwarf"),
	DWARF_EARED("Dwarf Eared", "Dwarf%20Eared"),
	ENGLISH_LOP("English Lop", "English%20Lop"),
	ENGLISH_SPOT("English Spot", "English%20Spot"),
	FLEMISH_GIANT("Flemish Giant", "Flemish%20Giant"),
	FLORIDA_WHITE("Florida White", "Florida%20White"),
	FRENCH_LOP("French-Lop", "French-Lop"),
	HARLEQUIN("Harlequin", "Harlequin"),
	HAVANA_RABBIT("Havana", "Havana"),
	HIMALAYAN_RABBIT("Himalayan", "Himalayan"),
	HOLLAND_LOP("Holland Lop", "Holland%20Lop"),
	HOTOT("Hotot", "Hotot"),
	JERSEY_WOOLY("Jersey Wooly", "Jersey%20Wooly"),
	LILAC("Lilac", "Lilac"),
	LIONHEAD("Lionhead", "Lionhead"),
	LOP_EARED("Lop Eared", "Lop%20Eared"),
	MINI_REX("Mini Rex", "Mini%20Rex"),
	MINI_LOP("Mini-Lop", "Mini-Lop"),
	NETHERLAND_DWARF("Netherland Dwarf", "Netherland%20Dwarf"),
	NEW_ZEALAND("New Zealand", "New%20Zealand"),
	PALOMINO_RABBIT("Palomino", "Palomino"),
	POLISH("Polish", "Polish"),
	REX("Rex", "Rex"),
	RHINELANDER("Rhinelander", "Rhinelander"),
	SATIN("Satin", "Satin"),
	SILVER_RABBIT("Silver", "Silver"),
	SILVER_FOX("Silver Fox", "Silver%20Fox"),
	SILVER_MARTEN("Silver Marten", "Silver%20Marten"),
	TAN("Tan", "Tan");

	String name;
	String value;

	RabbitType(String name, String value) {
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
