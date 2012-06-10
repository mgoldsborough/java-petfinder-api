package com.mg2.petfinder.schemaobjects;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "media")
public class Media {

    @ElementList(name = "photos", required = false)
    List<Photo> photos;

    public List<Photo> getPhotoList() {
	// return photos;
	return photos;
    }

    public void setPhotoList(List<Photo> photos) {
	this.photos = new ArrayList<Photo>();
	this.photos.addAll(photos);
    }

    public Photo getFirstXSmallPhoto() {
	for (Photo photo : photos) {
	    if (photo.getSize().equals(Photo.ID_XSMALL))
		return photo;
	}
	return null;
    }

    public Photo getFirstMedPhoto() {
	for (Photo photo : photos) {
	    if (photo.getSize().equals(Photo.ID_MED))
		return photo;
	}
	return null;
    }

    public Photo getFirstLargePhoto() {
	if (photos == null)
	    return null;

	for (Photo photo : photos) {
	    if (photo.getSize().equals(Photo.ID_LARGE))
		return photo;
	}
	return null;
    }

    public Photo getFirstXLargePhoto() {
	for (Photo photo : photos) {
	    if (photo.getSize().equals(Photo.ID_XLARGE))
		return photo;
	}
	return null;
    }

    public List<? extends Photo> getLargePhotoList() {
	List<Photo> largePhotoList = new ArrayList<Photo>();

	for (Photo photo : photos) {
	    if (photo.getSize().equals(Photo.ID_LARGE))
		largePhotoList.add(photo);
	}
	return largePhotoList;
    }

    public List<? extends Photo> getXLargePhotoList() {
	ArrayList<Photo> largePhotoList = new ArrayList<Photo>();

	for (Photo photo : photos) {
	    if (photo.getSize().equals(Photo.ID_XLARGE))
		largePhotoList.add(photo);
	}
	return largePhotoList;
    }
}
