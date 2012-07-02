package com.mg2.petfinder.wrappers;

public class StringWrapper {
    private String $t;

    public StringWrapper() {

    }

    public StringWrapper(String $t) {
	this.$t = $t;
    }

    @Override
    public String toString() {
	return $t;
    }
}
