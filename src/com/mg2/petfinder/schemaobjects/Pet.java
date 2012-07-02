package com.mg2.petfinder.schemaobjects;

import com.mg2.petfinder.schemaobjects.types.AgeType;
import com.mg2.petfinder.schemaobjects.types.AnimalType;
import com.mg2.petfinder.schemaobjects.types.GenderType;
import com.mg2.petfinder.schemaobjects.types.SizeType;
import com.mg2.petfinder.schemaobjects.types.StatusType;
import com.mg2.petfinder.wrappers.IntWrapper;
import com.mg2.petfinder.wrappers.StringWrapper;

public class Pet {

    protected IntWrapper id;
    protected StringWrapper name;
    protected StringWrapper age;
    protected StringWrapper sex;
    protected StringWrapper size;
    protected StringWrapper animal;
    protected StringWrapper description;
    protected StringWrapper mix;
    protected StringWrapper shelterId;
    protected StringWrapper shelterPetId;
    protected StringWrapper lastUpdate;
    protected StringWrapper status;

    protected Breeds breeds;
    protected Options options;

    protected Media media;
    protected Contact contact;

    public Pet() {
    }

    public int getId() {
	return this.id.getInt();
    }

    public String getName() {
	return name.toString();
    }

    public String getMix() {
	return mix.toString();
    }

    public String getShelterId() {
	return shelterId.toString();
    }

    public String getLastUpdate() {
	return lastUpdate.toString();
    }

    public StringWrapper getShelterPetId() {
	return shelterPetId;
    }

    public Media getMedia() {
	return media;
    }

    public Options getOptions() {
	return options;
    }

    @Override
    public int hashCode() {
	return id.getInt();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;

	if (!(obj instanceof Pet))
	    return false;

	Pet pet = (Pet) obj;

	if (id.getInt() == pet.getId()) {
	    return true;
	}
	return false;
    }

    public String getBreedListString() {
	return breeds.toString();
    }

    public String getOptionsListString() {
	return options.toString();
    }

    public String getDescription() {
	if (description.toString() == null || description.toString().isEmpty())
	    return "No description.";

	return description.toString().replace("<div>", "")
		.replace("</div>", "");
    }

    public Contact getContact() {
	return contact;
    }

    public AnimalType getAnimal() {
	return AnimalType.valueOf(animal.toString());
    }

    public GenderType getSex() {
	return GenderType.valueOf(sex.toString());

    }

    public SizeType getSize() {
	return SizeType.valueOf(size.toString());
    }

    public AgeType getAge() {
	for (AgeType type : AgeType.values()) {
	    if (type.getValue().equals(this.age))
		return type;
	}
	return null;
    }

    public StatusType getStatus() {
	for (StatusType type : StatusType.values()) {
	    if (type.getValue().equals(this.status))
		return type;
	}
	return null;
    }

    public String getShortDescription() {
	return String.format("%s/%s, %s", getAge().getName(), getSex()
		.getName(), getSize().getName());
    }

}
