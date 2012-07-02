package com.mg2.petfinder.adapters;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mg2.petfinder.wrappers.StringArrayWrapper;

public class StringArrayAdapter implements
	JsonDeserializer<StringArrayWrapper[]> {

    @Override
    public StringArrayWrapper[] deserialize(JsonElement json, Type typeOfT,
	    JsonDeserializationContext ctx) throws JsonParseException {

	System.out.println("Echo!");
	
	if (json.isJsonArray()) {
	    return new Gson().fromJson(json, StringArrayWrapper[].class);
	}
	StringArrayWrapper string = ctx.deserialize(json,
		StringArrayWrapper.class);
	return new StringArrayWrapper[] { string };
    }
}
