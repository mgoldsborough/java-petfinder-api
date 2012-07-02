package com.mg2.petfinder.wrappers;

public class StringArrayWrapper {
    private String $t;

    public StringArrayWrapper() {

    }

    public StringArrayWrapper(String $t) {
	this.$t = $t;
    }

    @Override
    public String toString() {
	return $t;
    }
}
