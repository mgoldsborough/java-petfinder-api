package com.mg2.petfinder.adapters;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mg2.petfinder.schemaobjects.Shelter;

public class ShelterArrayAdapter implements JsonDeserializer<Shelter[]> {

    @Override
    public Shelter[] deserialize(JsonElement json, Type typeOfT,
	    JsonDeserializationContext ctx) throws JsonParseException {

	if (json.isJsonArray()) {
	    return new Gson().fromJson(json, Shelter[].class);
	}
	Shelter shelter = ctx.deserialize(json, Shelter.class);
	return new Shelter[] { shelter };
    }
}
