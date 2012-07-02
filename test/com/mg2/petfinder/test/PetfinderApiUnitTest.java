package com.mg2.petfinder.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mg2.petfinder.PetfinderApi;
import com.mg2.petfinder.PetfinderApiResponseCode;
import com.mg2.petfinder.exceptions.InvalidResponseCodeException;
import com.mg2.petfinder.responseobjects.GetPet;
import com.mg2.petfinder.responseobjects.PetfinderResponse;
import com.mg2.petfinder.schemaobjects.Pet;
import com.mg2.petfinder.schemaobjects.Shelter;

public class PetfinderApiUnitTest extends TestCase {

    private String key;
    private String secret;

    private PetfinderApi api;

    @Override
    protected void setUp() throws Exception {
	System.out.println("Setup");

	Properties prop = new Properties();

	try {
	    prop.load(new FileInputStream("PetfinderApi.properties"));
	    key = prop.getProperty("API_KEY");
	    secret = prop.getProperty("API_SECRET");
	} catch (IOException ex) {
	    ex.printStackTrace();
	    fail("Could not load API Key/Secret");
	}
	System.out.println("Key " + key);
	System.out.println("Secret " + secret);

	api = new PetfinderApi(key, secret, true);

	super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
	System.out.println("Tear down");
	super.tearDown();
    }

    /**
     * Tests a successful authentication.
     */
    public void testAuthenticatinSuccess() {
	try {
	    api.Authenticate();
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException e) {
	    fail("JSON deserialize exception:" + e.getMessage());
	}
	assertTrue(api.isAuthenticated());
    }

    /**
     * Tests the failure case of isAuthenticated.
     */
    public void testAuthenticationFailure() {
	assertFalse(api.isAuthenticated());
    }

    /**
     * Tests successful retrieval of a pet.
     */
    public void testGetPetSuccess() {
	try {
	    PetfinderApi pf = new PetfinderApi(key, secret, true);

	    Pet pet = pf.GetPet(23151980,
		    new TypeToken<PetfinderResponse<GetPet<Pet>>>() {
		    }.getType());
	    System.out.println(pet.getOptions().toString());
	    assertNotNull(pet);
	    System.out.println(pet.toString());
	    System.out.println(pet.getBreedListString());
	    System.out.println(pet.getOptionsListString());
	} catch (InvalidResponseCodeException e) {
	    fail("We should not be here.. Check for valid pet ID");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException ex) {
	    fail("JSON deserialize exception:" + ex.getMessage());
	}
    }

    /**
     * Tests failure cause of getPet. Provides an invalid pet ID.
     */
    public void testGetPetInvalidPetID() {
	Pet pet = null;
	try {
	    pet = api.GetPet(99999999,
		    new TypeToken<PetfinderResponse<GetPet<Pet>>>() {
		    }.getType());
	} catch (InvalidResponseCodeException e) {
	    assertNotSame(e.getResponseCode(),
		    PetfinderApiResponseCode.PFAPI_OK.getResponseCode());
	    System.out.println(e.getMessage() + " " + e.getMessage());
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException e) {
	    fail("JSON deserialize exception:" + e.getMessage());
	}
	assertNull(pet);
    }

    /**
     * Tests getBreedList for a dog.
     */
    public void testGetBreedList() {
	try {
	    api.Authenticate();
	    String[] breedList = api.GetBreedList("dog");
	    assertNotNull(breedList);
	    for (String str : breedList) {
		System.out.println(str);
	    }
	} catch (InvalidResponseCodeException e) {
	    fail("We should not be here");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException ex) {
	    fail("JSON deserialize exception:" + ex.getMessage());
	}
    }

    public void testGetShelterSuccess() {

	String city = "New York, NY";
	try {
	    Shelter[] shelters = api.GetShelterList(city, 1);
	    assertNotNull(shelters);

	    for (Shelter shelter : shelters) {
		System.out.println(shelter.getName());
	    }
	} catch (InvalidResponseCodeException e) {
	    fail("Invalid response code.  Header was f'd");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException e) {
	    fail("JSON deserialize exception:" + e.getMessage());
	}
    }

    public void testGetShelterPetsSuccess() {

	try {
	    api.Authenticate();
	    Pet[] pets = api.GetShelterPets("DE20");
	    assertNotNull(pets);
	} catch (InvalidResponseCodeException e) {
	    e.printStackTrace();
	    fail("Invalid response code.  Header was f'd");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException e) {
	    fail("JSON deserialize exception:" + e.getMessage());
	}
    }

    public void testFindPets() {
	try {
	    // Provide params
	    Pet[] pets = api.FindPets("19806", null, null, "XL", "F", "Baby",
		    0, 2);

	    assertNotNull(pets);

	    if (pets.length < 1)
		fail("List does not contain any pets");

	    for (Pet pet : pets) {
		assertNotNull(pet);

		String[] urls = pet.getMedia().getPhotoUrlList();

		if (urls != null) {
		    for (String url : urls) {
			System.out.println("Url: " + url.toString());
		    }
		}
	    }

	    assertNotNull(pets);
	} catch (InvalidResponseCodeException e) {
	    e.printStackTrace();
	    fail("We should not be here.. Check for valid pet ID");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException e) {
	    fail("JSON deserialize exception:" + e.getMessage());
	}
    }

    public void testGetShelterListByBreed() {
	try {
	    Shelter[] shelters = api.GetShelterListByBreed("cat", "siamese");
	    assertNotNull(shelters);

	    for (Shelter shelter : shelters) {
		System.out.println(shelter.getName());
	    }
	} catch (InvalidResponseCodeException e) { //
	    e.printStackTrace();
	    fail("Invalid response code.  Header was f'd");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (JsonParseException e) {
	    fail("JSON deserialize exception:" + e.getMessage());
	}
    }
}