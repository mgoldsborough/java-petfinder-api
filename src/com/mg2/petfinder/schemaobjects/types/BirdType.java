package com.mg2.petfinder.schemaobjects.types;


public enum BirdType implements IBreedType {
	// Bird types
	AFRICAN_GREY("African Grey", "African%20Grey"),
	AMAZON("Amazon", "Amazon"),
	BROTOGERIS("Brotogeris", "Brotogeris"),
	BUDGIE_BUDGERIGAR("Budgie/Budgerigar", "Budgie%2FBudgerigar"),
	BUTTON_QUAIL("Button Quail", "Button%20Quail"),
	CAIQUE("Caique", "Caique"),
	CANARY("Canary", "Canary"),
	CHICKEN("Chicken", "Chicken"),
	COCKATIEL("Cockatiel", "Cockatiel"),
	COCKATOO("Cockatoo", "Cockatoo"),
	CONURE("Conure", "Conure"),
	DOVE("Dove", "Dove"),
	DUCK("Duck", "Duck"),
	ECLECTUS("Eclectus", "Eclectus"),
	EMU("Emu", "Emu"),
	FINCH("Finch", "Finch"),
	GOOSE("Goose", "Goose"),
	GUINEA_FOWL("Guinea fowl", "Guinea%20fowl"),
	KAKARIKI("Kakariki", "Kakariki"),
	LORY_LORIKEET("Lory/Lorikeet", "Lory%2FLorikeet"),
	LOVEBIRD("Lovebird", "Lovebird"),
	MACAW("Macaw", "Macaw"),
	MYNAH("Mynah", "Mynah"),
	OSTRICH("Ostrich", "Ostrich"),
	PARAKEET_OTHER("Parakeet (Other)", "Parakeet%20(Other)"),
	PARROT_OTHER("Parrot (Other)", "Parrot%20(Other)"),
	PARROTLET("Parrotlet", "Parrotlet"),
	PEACOCK_PEA_FOWL("Peacock/Pea fowl", "Peacock%2FPea%20Fowl"),
	PHEASANT("Pheasant", "Pheasant"),
	PIGEON("Pigeon", "Pigeon"),
	PIONUS("Pionus", "Pionus"),
	POICEPHALUS_SENEGAL("Poicephalus/Senegal", "Poicephalus%2FSenegal"),
	QUAIL("Quail", "Quail"),
	QUAKER_PARAKEET("Quaker Parakeet", "Quaker%20Parakeet"),
	RHEA("Rhea", "Rhea"),
	RINGNECK_PSITTACULA("Ringneck/Psittacula", "Ringneck%2FPsittacula"),
	ROSELLA("Rosella", "Rosella"),
	SOFTBILL_OTHER("Softbill (Other)", "Softbill%20(Other)");
	
	String name;
	String value;
	
	BirdType(String name, String value) {
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
