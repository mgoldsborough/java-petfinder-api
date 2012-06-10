package com.mg2.petfinder.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.mg2.petfinder.PetfinderApi;
import com.mg2.petfinder.PetfinderApiResponseCode;
import com.mg2.petfinder.exceptions.DeserializationException;
import com.mg2.petfinder.exceptions.InvalidResponseCodeException;
import com.mg2.petfinder.schemaobjects.Pet;
import com.mg2.petfinder.schemaobjects.Photo;
import com.mg2.petfinder.schemaobjects.Shelter;

public class PetfinderApiUnitTest extends TestCase {

    private static final Logger log = Logger
	    .getLogger(PetfinderApiUnitTest.class);

    private String key;
    private String secret;

    private PetfinderApi api;

    @Override
    protected void setUp() throws Exception {
	log.info("Setup");

	Properties prop = new Properties();

	try {
	    prop.load(new FileInputStream("PetfinderApi.properties"));
	    key = prop.getProperty("API_KEY");
	    secret = prop.getProperty("API_SECRET");
	} catch (IOException ex) {
	    ex.printStackTrace();
	    fail("Could not load API Key/Secret");
	}
	log.info("Key " + key);
	log.info("Secret " + secret);

	api = new PetfinderApi(key, secret);

	super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
	log.info("Tear down");
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
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
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
	    PetfinderApi pf = new PetfinderApi(key, secret);
	    Pet pet = pf.GetPet(21183885);
	    assertNotNull(pet);
	    log.info(pet.toString());
	} catch (InvalidResponseCodeException e) {
	    fail("We should not be here.. Check for valid pet ID");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
	}
    }

    /**
     * Tests failure cause of getPet. Provides an invalid pet ID.
     */
    public void testGetPetInvalidPetID() {
	Pet pet = null;
	try {
	    pet = api.GetPet(99999999);
	} catch (InvalidResponseCodeException e) {
	    assertNotSame(e.getResponseCode(),
		    PetfinderApiResponseCode.PFAPI_OK.getResponseCode());
	    log.info(e.getMessage() + " " + e.getMessage());
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
	}
	assertNull(pet);
    }

    /**
     * Tests getBreedList for a dog.
     */
    public void testGetBreedList() {
	try {
	    api.Authenticate();
	    List<String> breedList = api.GetBreedList("dog");
	    assertNotNull(breedList);
	} catch (InvalidResponseCodeException e) {
	    fail("We should not be here");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
	}
    }

    public void testGetShelterSuccess() {

	String city = "New York, NY";
	try {
	    List<Shelter> shelters = api.GetShelterList(city, 1);
	    assertNotNull(shelters);
	} catch (InvalidResponseCodeException e) {
	    fail("Invalid response code.  Header was f'd");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
	}
    }

    public void testGetShelterPetsSuccess() { // Shelter opt out: WY57

	try {
	    api.Authenticate();
	    List<Pet> pets = api.GetShelterPets("DE20");
	    assertNotNull(pets);
	} catch (InvalidResponseCodeException e) {
	    e.printStackTrace();
	    fail("Invalid response code.  Header was f'd");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
	}
    }

    public void testFindPets() {
	try {
	    PetfinderApi pf = new PetfinderApi(key, secret, true);

	    // Provide params
	    List<Pet> pets = pf.FindPets("11731", null, null, "XL", "F",
		    "Baby", 0, -1);

	    assertNotNull(pets);

	    if (pets.size() < 1)
		fail("List does not contain any pets");

	    for (Pet pet : pets) {
		assertNotNull(pet);

		List<Photo> photos = pet.getMedia().getPhotoList(); //

		if (photos != null) {
		    for (Photo photo : photos) {
			assertNotNull(photo.getId());
			log.info("Id: " + photo.getId());

			assertNotNull(photo.getSize());
			log.info("Size: " + photo.getSize());

			assertNotNull(photo.getUrlString());
			log.info("Url: " + photo.getUrlString());
		    }
		}
	    }

	    assertNotNull(pets);
	} catch (InvalidResponseCodeException e) {
	    fail("We should not be here.. Check for valid pet ID");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
	}
    }

    /**
     * DOES NOT WORK.
     */
    public void testGetShelterListByBreed() {
	try {
	    List<Shelter> shelters = api
		    .GetShelterListByBreed("cat", "siamese");
	    assertNotNull(shelters);
	} catch (InvalidResponseCodeException e) { //
	    e.printStackTrace();
	    fail("Invalid response code.  Header was f'd");
	} catch (IOException e) {
	    fail("IOException: Timeout on HTTP GET most likely.. ");
	} catch (DeserializationException ex) {
	    fail("XML deserialize exception:" + ex.getMessage());
	}
    }
}