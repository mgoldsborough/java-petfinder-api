package com.mg2.petfinder.schemaobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "photo")
public class Photo {
	public static final String ID_XSMALL = "pnt";
	public static final String ID_SMALL = "t";
	public static final String ID_MED = "fpm";
	public static final String ID_LARGE = "pn";
	public static final String ID_XLARGE = "x";

	@Attribute(name = "id")
	protected int id;

	@Attribute(name = "size")
	protected String size;

	@Text
	protected String url;

	// Blank constructor for xstream
	public Photo() {

	}

	public int getId() {
		return this.id;
	}

	public String getSize() {
		return this.size;
	}

	public String getUrlString() {
		return this.url;
	}

	public int describeContents() {
		return 0;
	}

}
