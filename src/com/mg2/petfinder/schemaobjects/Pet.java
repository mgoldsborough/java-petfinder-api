package com.mg2.petfinder.schemaobjects;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.mg2.petfinder.schemaobjects.types.AgeType;
import com.mg2.petfinder.schemaobjects.types.GenderType;
import com.mg2.petfinder.schemaobjects.types.SizeType;
import com.mg2.petfinder.schemaobjects.types.StatusType;

@Root(name = "pet")
public class Pet {

    public Pet() {
    }

    @Element
    protected int id;

    @Element
    protected String shelterId;

    @Element(required = false)
    protected String shelterPetId;

    @Element
    protected String name;

    @Element
    protected String animal;

    @ElementList(name = "breeds")
    protected List<String> breeds;

    @Element
    protected String mix;

    @Element
    protected String age;

    @Element
    protected String sex;

    @Element
    protected String size;

    @ElementList(name = "options")
    protected List<String> options;

    @Element(name = "description", required = false)
    protected String description;

    @Element(name = "lastUpdate")
    protected String lastUpdate;

    @Element(name = "status")
    protected String status;

    @Element(name = "media", required = false)
    protected Media media;

    @Element(name = "contact")
    protected Contact contact;

    public String getName() {
	return this.name;
    }

    @Override
    public String toString() {
	return this.name;
    }

    public int getId() {
	return this.id;
    }

    public Media getMedia() {
	return this.media;
    }

    public String getBreedListString() {
	if (breeds == null || breeds.size() == 0)
	    return "";

	String prefix = "";
	StringBuilder sb = new StringBuilder();
	for (String breed : breeds) {
	    sb.append(prefix);
	    sb.append(breed);
	    prefix = ", ";
	}
	return sb.toString();
    }

    public String getOptionsListString() {
	if (options == null || options.size() == 0)
	    return "None";
	String prefix = "";
	StringBuilder sb = new StringBuilder();

	for (String option : options) {
	    sb.append(prefix);
	    sb.append(option);
	    prefix = ", ";
	}
	return sb.toString();
    }

    public String getDescription() {
	if (description == null)
	    return "No description.";

	return description.replace("<div>", "").replace("</div>", "");
    }

    public Contact getContact() {
	return contact;
    }

    public String getAnimal() {
	return animal.toString();
    }

    public GenderType getSex() {
	for (GenderType type : GenderType.values()) {
	    if (type.getValue().equals(this.sex))
		return type;
	}

	return null;
    }

    public SizeType getSize() {
	for (SizeType type : SizeType.values()) {
	    if (type.getValue().equals(this.size))
		return type;
	}
	return null;
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

    @Override
    public int hashCode() {
	return this.id;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;

	if (!(obj instanceof Pet))
	    return false;

	Pet pet = (Pet) obj;

	if (this.id == pet.getId()) {
	    return true;
	}
	return false;
    }
}
