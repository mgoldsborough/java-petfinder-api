package com.mg2.petfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.mg2.petfinder.exceptions.DeserializationException;
import com.mg2.petfinder.exceptions.InvalidResponseCodeException;
import com.mg2.petfinder.responseobjects.AuthenticateResponse;
import com.mg2.petfinder.responseobjects.GetBreedListResponse;
import com.mg2.petfinder.responseobjects.GetPetListResponse;
import com.mg2.petfinder.responseobjects.GetPetResponse;
import com.mg2.petfinder.responseobjects.GetShelterListByBreedResponse;
import com.mg2.petfinder.responseobjects.GetShelterListResponse;
import com.mg2.petfinder.responseobjects.GetShelterPetsResponse;
import com.mg2.petfinder.responseobjects.GetShelterResponse;
import com.mg2.petfinder.schemaobjects.Pet;
import com.mg2.petfinder.schemaobjects.Shelter;

public final class PetfinderApi {

    private static final String URL = "http://api.petfinder.com/";

    private boolean debug = false;

    private static final Random randInt = new Random();

    // Formatter for date/times returned from Petfinder API
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
	    "yyyy-MM-dd'T'HH:mm:ss");

    private String authToken = null;
    private Date tokenExpires = null;

    private List<Pet> lastPetResponse = null;
    private int lastOffset = 0;

    private String[] keys;
    private String[] secrets;
    private int numKeys;

    /**
     * 
     * @param apiKey
     * @param secret
     */
    public PetfinderApi(String apiKey, String secret) {
	keys = new String[] { apiKey };
	secrets = new String[] { secret };
	numKeys = 1;
	this.debug = false;
    }

    /**
     * 
     * @param apiKey
     * @param secret
     * @param debug
     */
    public PetfinderApi(String apiKey, String secret, boolean debug) {
	keys = new String[] { apiKey };
	secrets = new String[] { secret };
	numKeys = 1;
	this.debug = debug;
    }

    /**
     * 
     * @param keys
     * @param secrets
     */
    public PetfinderApi(String[] keys, String[] secrets) {
	this.keys = keys;
	this.secrets = secrets;
	this.numKeys = keys.length;
	this.debug = false;
    }

    /**
     * 
     * @param keys
     * @param secrets
     * @param debug
     */
    public PetfinderApi(String[] keys, String[] secrets, boolean debug) {
	this.keys = keys;
	this.secrets = secrets;
	this.numKeys = keys.length;
	this.debug = debug;
    }

    /**
     * Authenticates against PetfinderAPI. Sets the authToken and tokenExpires
     * class attributes.
     * 
     * @throws IOException
     *             Thrown when the request to the PF API was unsuccessful.
     * @throws DeserializationException
     *             Thrown when the response could be be deserialized.
     */
    public void Authenticate() throws IOException, DeserializationException {

	final String URI = genUri(PetfinderApiMethod.AUTHENTICATE, null);

	if (debug)
	    System.out.println("PetfinderUri: " + URI);

	// HTTP GET URI
	String response = httpGET(URI);

	// Serialize XML, get authorization token and when it expires.
	// Save to class variables
	AuthenticateResponse authResponse = deserialize(
		AuthenticateResponse.class, response);

	// Get auth token and parse datetime from response into Java date object
	authToken = authResponse.getAuthToken();
	try {
	    tokenExpires = formatter.parse(authResponse.getDateExpiresString());

	    if (debug)
		System.out.println(String.format(
			"AuthToken: '%s', Timestamp: '%s'", authToken,
			tokenExpires));
	} catch (ParseException ex) {
	    System.out.println(String.format(
		    "Parse Exception on timestamp: '%s'",
		    authResponse.getDateExpiresString(), ex));
	}

	return;
    }

    /**
     * Obtains a single pet based off the id provided. If the URL you desire is
     * http://www.petfinder.com/petdetail/22146300, the id would be 22146300.
     * 
     * @param id
     *            The ID of the pet (integer value from URL).
     * @return The {@link Pet}.
     * @throws InvalidResponseCodeException
     *             Thrown when something other than
     *             {@link PetfinderApiResponseCode#PFAPI_OK} is returned as a
     *             response code.
     * @throws IOException
     *             Thrown when the request to the PF API was unsuccessful.
     * @throws DeserializationException
     *             Thrown when the response could be be deserialized.
     */
    public Pet GetPet(int id) throws InvalidResponseCodeException, IOException,
	    DeserializationException {

	// Set method/args for request
	final String[] args = { "id=" + id };
	// "token=" + this.authToken };

	// Generate URI
	final String URI = genUri(PetfinderApiMethod.GET_PET, args);

	// Perform HTTP GET
	String response = httpGET(URI);

	// Parse XML
	GetPetResponse getPetResponse = deserialize(GetPetResponse.class,
		response);

	// Validate. Throws InvalidResponseCode Exception
	getPetResponse.validate();

	return getPetResponse.getPet();
    }

    /**
     * Obtains a random pet from PF. All params are optional <br/>
     * <br/>
     * NOTE: I believe PF has an error on their end which makes this method
     * unusable.
     * 
     * @param location
     *            The location (optional).
     * @param animal
     *            The animal type (optional).
     * @param breed
     *            The breed type (optional).
     * @param size
     *            The size (optional).
     * @param sex
     *            The sex (optional).
     * 
     * @param shelter
     *            The shelter (optional).
     * @return A {@link Pet}
     * @throws InvalidResponseCodeException
     *             Thrown when something other than
     *             {@link PetfinderApiResponseCode#PFAPI_OK} is returned as a
     *             response code.
     * @throws IOException
     *             Thrown when the request to the PF API was unsuccessful.
     * @throws DeserializationException
     *             Thrown when the response could be be deserialized.
     */
    public Pet GetRandomPet(String location, String animal, String breed,
	    String size, String sex, String shelter)
	    throws InvalidResponseCodeException, IOException,
	    DeserializationException {
	List<String> argList = new ArrayList<String>();

	if (!(animal == null || animal.equals("")))
	    argList.add("animal=" + animal.toLowerCase());

	if (!(breed == null || breed.equals("")))
	    argList.add("breed=" + breed);

	if (!(size == null || size.equals("")))
	    argList.add("size=" + size);

	if (!(sex == null || sex.equals("")))
	    argList.add("sex=" + sex);

	if (!(location == null || location.equals("")))
	    argList.add("location=" + location);

	if (!(shelter == null || shelter.equals("")))
	    argList.add("shelter=" + shelter);

	// TODO: Make this a params. Right now there is no need to return
	// anything other than the full response.
	argList.add("output=full");

	// Generate URI
	String[] args = new String[argList.size()];
	argList.toArray(args);

	final String URI = genUri(PetfinderApiMethod.GET_PET_RANDOM, args);

	String response = httpGET(URI);

	// Parse XML
	GetPetResponse getPetResponse = deserialize(GetPetResponse.class,
		response);

	// Validate. Throws InvalidResponseCode Exception
	getPetResponse.validate();

	return getPetResponse.getPet();

    }

    /**
     * Retrieves a list of pets based on the parameters provided.
     * 
     * @param location
     *            The location (optional).
     * @param animal
     *            The animal type (optional).
     * @param breed
     *            The breed type (optional).
     * @param size
     *            The size (optional).
     * @param sex
     *            The sex (optional).
     * @param offset
     *            The offset used to paginate through the results.
     * @param count
     *            The number of results to return.
     * @return
     * @return A list of {@link Pet} objects.
     * @throws InvalidResponseCodeException
     *             Thrown when something other than
     *             {@link PetfinderApiResponseCode#PFAPI_OK} is returned as a
     *             response code.
     * @throws IOException
     *             Thrown when the request to the PF API was unsuccessful.
     * @throws DeserializationException
     *             Thrown when the response could be be deserialized.
     */
    public List<? extends Pet> FindPets(String location, String animal,
	    String breed, String size, String sex, String age, int offset,
	    int count) throws IOException, DeserializationException,
	    InvalidResponseCodeException {
	List<String> argList = new ArrayList<String>();

	if (!(location == null || location.equals("")))
	    argList.add("location=" + location.replace(" ", ""));

	if (!(animal == null || animal.equals("")))
	    argList.add("animal=" + animal.toLowerCase());

	if (!(breed == null || breed.equals("")))
	    argList.add("breed=" + breed);

	if (!(size == null || size.equals("")))
	    argList.add("size=" + size);

	if (!(sex == null || sex.equals("")))
	    argList.add("sex=" + sex);
	if (!(age == null || age.equals("")))
	    argList.add("age=" + age);

	if (offset != -1)
	    argList.add("offset=" + offset);

	if (count != -1)
	    argList.add("count=" + Integer.toString(count));

	// Generate URI
	String[] args = new String[argList.size()];
	argList.toArray(args);

	final String URI = genUri(PetfinderApiMethod.FIND_PET, args);

	String response = httpGET(URI);

	// Deserialize XML
	GetPetListResponse getPetsResponse = deserialize(
		GetPetListResponse.class, response);

	// Validate. Throws InvalidResponseCode Exception
	getPetsResponse.validate();

	lastPetResponse = getPetsResponse.getPetList();
	lastOffset = getPetsResponse.getLastOffset();

	return getPetsResponse.getPetList();
    }

    /**
     * Obtains a list of shelters based on the parameters provided.
     * 
     * @param location
     *            The location.
     * @param count
     *            The number of shelters to return.
     * @return A list of {@link Shelter} objects.
     * @throws InvalidResponseCodeException
     *             Thrown when something other than
     *             {@link PetfinderApiResponseCode#PFAPI_OK} is returned as a
     *             response code.
     * @throws IOException
     *             Thrown when the request to the PF API was unsuccessful.
     * @throws DeserializationException
     *             Thrown when the response could be be deserialized.
     */
    public List<Shelter> GetShelterList(String location, int count)
	    throws IOException, DeserializationException,
	    InvalidResponseCodeException {
	List<String> argList = new ArrayList<String>();

	if (!(location == null || location.equals("")))
	    argList.add("location=" + URLEncoder.encode(location, "UTF-8"));

	if (count != -1)
	    argList.add("count=" + Integer.toString(count));

	String[] args = new String[argList.size()];
	argList.toArray(args);

	final String URI = genUri(PetfinderApiMethod.GET_SHELTER_LIST, args);

	String response = httpGET(URI);

	GetShelterListResponse getSheltersResponse = deserialize(
		GetShelterListResponse.class, response);

	getSheltersResponse.validate();

	return getSheltersResponse.getShelterList();

    }

    /**
     * Gets a list of Breeds as a string based on the animal provided.
     * 
     * @param animal
     *            The type of animal.
     * @return A list of strings corresponding to the animals breed.
     * @throws InvalidResponseCodeException
     *             Thrown when something other than
     *             {@link PetfinderApiResponseCode#PFAPI_OK} is returned as a
     *             response code.
     * @throws IOException
     *             Thrown when the request to the PF API was unsuccessful.
     * @throws DeserializationException
     *             Thrown when the response could be be deserialized.
     */
    public List<String> GetBreedList(String animal)
	    throws InvalidResponseCodeException, IOException,
	    DeserializationException {

	// Generate args
	final String[] args = { "animal=" + animal };
	// "token=" + this.authToken };
	final String URI = genUri(PetfinderApiMethod.GET_BREED_LIST, args);

	String response = httpGET(URI);

	// Parse XML
	GetBreedListResponse getBreedListResponse = deserialize(
		GetBreedListResponse.class, response);
	getBreedListResponse.validate();

	return getBreedListResponse.getBreedList();
    }

    /**
     * 
     * @param id
     * @return
     * @throws InvalidResponseCodeException
     * @throws IOException
     * @throws DeserializationException
     */
    public Shelter GetShelter(String id) throws InvalidResponseCodeException,
	    IOException, DeserializationException {
	// Generate args
	final String[] args = { "id=" + id };
	final String URI = genUri(PetfinderApiMethod.GET_SHELTER, args);

	String response = httpGET(URI);

	// Parse XML
	GetShelterResponse getShelterResponse = deserialize(
		GetShelterResponse.class, response);
	getShelterResponse.validate();

	return getShelterResponse.getShelter();
    }

    /**
     * 
     * @param id
     * @return
     * @throws InvalidResponseCodeException
     * @throws IOException
     * @throws DeserializationException
     */
    public List<Pet> GetShelterPets(String id)
	    throws InvalidResponseCodeException, IOException,
	    DeserializationException {
	// Generate args
	final String[] args = { "id=" + id };
	final String URI = genUri(PetfinderApiMethod.GET_SHELTER_PETS, args);

	String response = httpGET(URI);

	// Parse XML
	GetShelterPetsResponse getShelterPetsResponse = deserialize(
		GetShelterPetsResponse.class, response);
	getShelterPetsResponse.validate();

	// return getShelterPetsResponse.getPetList();
	return new ArrayList<Pet>();
    }

    /**
     * 
     * @param animal
     * @param breed
     * @return
     * @throws InvalidResponseCodeException
     * @throws IOException
     * @throws DeserializationException
     */
    public List<Shelter> GetShelterListByBreed(String animal, String breed)
	    throws InvalidResponseCodeException, IOException,
	    DeserializationException {
	// Generate args
	final String[] args = { "animal=" + animal, "breed=" + breed };
	final String URI = genUri(PetfinderApiMethod.GET_SHELTER_LIST_BY_BREED,
		args);

	String response = httpGET(URI);

	// Parse XML
	GetShelterListByBreedResponse getShelterListByBreedResponse = deserialize(
		GetShelterListByBreedResponse.class, response);
	getShelterListByBreedResponse.validate();

	return getShelterListByBreedResponse.getPetList();
    }

    /**
     * Generates an MD5 hash of the provided string.
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private String getMD5(String str) throws UnsupportedEncodingException,
	    NoSuchAlgorithmException {
	byte[] bytesOfMessage = str.getBytes("UTF-8");

	MessageDigest md = MessageDigest.getInstance("MD5");
	byte[] thedigest = md.digest(bytesOfMessage);

	BigInteger hash = new BigInteger(1, thedigest);
	String hashText = hash.toString(16);
	while (hashText.length() < 32) {
	    hashText = "0" + hashText;
	}
	return hashText;
    }

    /**
     * Takes args, concatenates and MD5's them, signs arguments, prepends method
     * and creates proper URI for request.
     * 
     * @param method
     *            HTTP method
     * @param args
     *            String of <key=value> pairs to add to URI
     * @return URI for the request
     */
    private String genUri(PetfinderApiMethod method, String[] args) {
	// Concatenate method and all args with an '&' in between.
	StringBuilder builder = new StringBuilder();

	// Generate random index from 0 to numKeys-1 to randomly use multiple
	// API key/secret combinations. PF only allows 10k hits a day.
	final int keyId = randInt.nextInt(numKeys);

	if (debug)
	    System.out.println(String.format("Key: '%s', Secret: '%s'",
		    keys[keyId], secrets[keyId]));

	if (args != null) {
	    // Loop over all args and append them to StringBuilder
	    String prefix = "";
	    for (String arg : args) {
		builder.append(prefix);
		prefix = "&";
		builder.append(arg);
	    }
	}

	// Append key
	builder.append("&key=" + keys[keyId]);

	final String urn = builder.toString();

	if (debug)
	    System.out.println("URN: " + urn);

	final String signature = genSignature(secrets[keyId], urn);
	final String URI = URL + method + urn + "&sig=" + signature;

	return URI;
    }

    /**
     * Performs an HTTP get on a provided URI.
     * 
     * @param uri
     * @return
     * @throws IOException
     */
    private String httpGET(String uri) throws IOException {
	try {
	    URL url = new URL(uri);

	    BufferedReader in = new BufferedReader(new InputStreamReader(
		    url.openStream()));
	    StringBuilder builder = new StringBuilder();
	    String str;

	    while ((str = in.readLine()) != null) {

		if (debug)
		    System.out.println(str);

		builder.append(str);
	    }

	    in.close();
	    return builder.toString();
	} catch (MalformedURLException e) {
	    System.out.println("MalformedURLException: " + uri);
	} catch (IOException e) {
	    System.out.println("IOException trying to GET: " + uri);
	    throw e;
	}
	return "";
    }

    /**
     * 
     * @return
     */
    public boolean isAuthenticated() {
	if (authToken == null || tokenExpires == null)
	    return false;

	// Token has expired, reset authentication
	else if (tokenExpires.compareTo(Calendar.getInstance().getTime()) < 0) {
	    try {
		System.out
			.println("Auth token is expired. Resetting authentication.");
	    } catch (NoClassDefFoundError ex) {
		System.out
			.println("Auth token is expired. Resetting authentication");
	    }
	    resetAuthentication();
	    return false;
	}
	// Valid token, return true
	return true;
    }

    /**
     * Generates a signature for the request.
     * 
     * @param secret
     * @param args
     * @return
     */
    private String genSignature(final String secret, String args) {
	try {
	    return getMD5(secret + args);
	} catch (Exception e) {
	    // Process exception
	}
	return "";
    }

    /**
     * Obtains a single pet from the last pet list response.
     * 
     * @param index
     *            The index of the pet to retrieve.
     * @return A {@link Pet} object.
     */
    public Pet getPetFromLastResponse(int index) {
	return lastPetResponse.get(index);
    }

    /**
     * Returns the offset from the last PF API call. Used to paginate through
     * results.
     * 
     * @return
     */
    public int getLastOffset() {
	return lastOffset;
    }

    /**
     * Resets the last offset.
     */
    public void resetLastOffset() {
	lastOffset = 0;
    }

    /**
     * Resets the PF API authentication parameters.
     */
    private void resetAuthentication() {
	authToken = null;
	tokenExpires = null;
    }

    /**
     * Deserializes a response object.
     * 
     * @param c
     *            The object Class to deserialize into.
     * @param response
     *            The XML response.
     * @return The desired object.
     * @throws DeserializationException
     *             Thrown when the response could be be deserialized.
     */
    private <T> T deserialize(Class<T> c, String response)
	    throws DeserializationException {
	try {
	    Serializer serializer = new Persister();
	    return serializer.read(c, response);
	} catch (Exception ex) {
	    throw new DeserializationException(ex.getMessage());
	}
    }
}
