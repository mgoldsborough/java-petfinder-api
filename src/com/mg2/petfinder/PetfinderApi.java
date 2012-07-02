package com.mg2.petfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mg2.petfinder.adapters.ShelterArrayAdapter;
import com.mg2.petfinder.adapters.StringArrayAdapter;
import com.mg2.petfinder.exceptions.InvalidResponseCodeException;
import com.mg2.petfinder.responseobjects.FindPets;
import com.mg2.petfinder.responseobjects.GetBreedList;
import com.mg2.petfinder.responseobjects.GetPet;
import com.mg2.petfinder.responseobjects.GetShelter;
import com.mg2.petfinder.responseobjects.GetShelterList;
import com.mg2.petfinder.responseobjects.GetShelterListByBreed;
import com.mg2.petfinder.responseobjects.GetShelterPets;
import com.mg2.petfinder.responseobjects.GetToken;
import com.mg2.petfinder.responseobjects.PetfinderResponse;
import com.mg2.petfinder.schemaobjects.Pet;
import com.mg2.petfinder.schemaobjects.Shelter;
import com.mg2.petfinder.wrappers.StringArrayWrapper;

public final class PetfinderApi {

    private static final String URL = "http://api.petfinder.com/";

    private boolean debug = false;

    private static final Random randInt = new Random();

    private String authToken = null;
    private Date tokenExpires = null;

    private Pet[] lastPetResponse = null;
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
     * @throws JsonParseException
     *             Thrown when the JSON response from Petfinder could not be
     *             parsed by the GSON parser.
     */
    public void Authenticate() throws IOException, JsonParseException {

	final String URI = genUri(PetfinderApiMethod.AUTHENTICATE, null);

	if (debug)
	    System.out.println("PetfinderUri: " + URI);

	// HTTP GET URI
	String response = httpGET(URI);

	Gson gson = buildGson();

	Type type = new TypeToken<PetfinderResponse<GetToken>>() {
	}.getType();

	PetfinderResponse<GetToken> authResponse = gson
		.fromJson(response, type);

	// Get auth token and parse datetime from response into Java date object
	authToken = authResponse.getPetfinder().getAuth().getToken();
	tokenExpires = authResponse.getPetfinder().getAuth().getTokenExpires();

	if (debug)
	    System.out.println(String
		    .format("AuthToken: '%s', Timestamp: '%s'", authToken,
			    tokenExpires));

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
     * @throws JsonParseException
     *             Thrown when the JSON response from Petfinder could not be
     *             parsed by the GSON parser.
     */
    public <T extends Pet> T GetPet(int id, Type type)
	    throws InvalidResponseCodeException, IOException,
	    JsonParseException {

	final String[] args = { String.format("id=%s", id) };

	// Generate URI
	final String URI = genUri(PetfinderApiMethod.GET_PET, args);

	// Perform HTTP GET
	String jsonStr = httpGET(URI);

	Gson gson = buildGson();

	PetfinderResponse<GetPet<T>> response = new PetfinderResponse<GetPet<T>>();

	// Type type = new TypeToken<PetfinderResponse<GetPet<T>>>() {
	// }.getType();

	response = gson.fromJson(jsonStr, type);

	// Validate. Throws InvalidResponseCode Exception
	response.getPetfinder().validate();

	return response.getPetfinder().getPet();
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
     * @throws JsonParseException
     *             Thrown when the JSON response from Petfinder could not be
     *             parsed by the GSON parser.
     */
    public <T extends Pet> T GetRandomPet(String location, String animal,
	    String breed, String size, String sex, String shelter)
	    throws InvalidResponseCodeException, IOException,
	    JsonParseException {
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

	Gson gson = buildGson();

	Type type = new TypeToken<PetfinderResponse<GetPet<Pet>>>() {
	}.getType();

	// Parse XML
	PetfinderResponse<GetPet<T>> getPetResponse = gson.fromJson(response,
		type);

	// Validate. Throws InvalidResponseCode Exception
	getPetResponse.getPetfinder().validate();

	return getPetResponse.getPetfinder().getPet();
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
     * @throws JsonParseException
     *             Thrown when the JSON response from Petfinder could not be
     *             parsed by the GSON parser.
     */
    public <T extends Pet> T[] FindPets(String location, String animal,
	    String breed, String size, String sex, String age, int offset,
	    int count) throws IOException, JsonParseException,
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

	Type type = new TypeToken<PetfinderResponse<FindPets<Pet>>>() {
	}.getType();

	Gson gson = buildGson();

	PetfinderResponse<FindPets<T>> getPetsResponse = gson.fromJson(
		response, type);

	// Validate. Throws InvalidResponseCode Exception
	getPetsResponse.getPetfinder().validate();

	lastPetResponse = getPetsResponse.getPetfinder().getPets();
	lastOffset = getPetsResponse.getPetfinder().getLastOffset();

	return getPetsResponse.getPetfinder().getPets();
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
     * @throws JsonParseException
     *             Thrown when the JSON response from Petfinder could not be
     *             parsed by the GSON parser.
     */
    public Shelter[] GetShelterList(String location, int count)
	    throws IOException, JsonParseException,
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

	Gson gson = buildGson();

	Type type = new TypeToken<PetfinderResponse<GetShelterList>>() {
	}.getType();

	PetfinderResponse<GetShelterList> getSheltersResponse = gson.fromJson(
		response, type);

	getSheltersResponse.validate();

	return getSheltersResponse.getPetfinder().getShelters();
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
     * @throws JsonParseException
     *             Thrown when the JSON response from Petfinder could not be
     *             parsed by the GSON parser.
     */
    public String[] GetBreedList(String animal)
	    throws InvalidResponseCodeException, IOException,
	    JsonParseException {

	// Generate args
	final String[] args = { "animal=" + animal };
	// "token=" + this.authToken };
	final String URI = genUri(PetfinderApiMethod.GET_BREED_LIST, args);

	String response = httpGET(URI);

	Gson gson = buildGson();

	Type type = new TypeToken<PetfinderResponse<GetBreedList>>() {
	}.getType();

	// Parse XML
	PetfinderResponse<GetBreedList> getBreedListResponse = gson.fromJson(
		response, type);

	getBreedListResponse.getPetfinder().validate();

	return getBreedListResponse.getPetfinder().getBreeds().getBreeds();
    }

    /**
     * 
     * @param id
     * @return
     * @throws InvalidResponseCodeException
     * @throws IOException
     * @throws JsonParseException
     */
    public Shelter GetShelter(String id) throws InvalidResponseCodeException,
	    IOException, JsonParseException {
	// Generate args
	final String[] args = { "id=" + id };
	final String URI = genUri(PetfinderApiMethod.GET_SHELTER, args);

	String response = httpGET(URI);

	Gson gson = buildGson();

	Type type = new TypeToken<PetfinderResponse<GetShelter>>() {
	}.getType();

	GetShelter getShelterResponse = gson.fromJson(response, type);
	getShelterResponse.validate();

	return getShelterResponse.getShelter();
    }

    /**
     * 
     * @param id
     * @return
     * @throws InvalidResponseCodeException
     * @throws IOException
     * @throws JsonParseException
     */
    public <T extends Pet> T[] GetShelterPets(String id)
	    throws InvalidResponseCodeException, IOException,
	    JsonParseException {
	// Generate args
	final String[] args = { "id=" + id };
	final String URI = genUri(PetfinderApiMethod.GET_SHELTER_PETS, args);

	String response = httpGET(URI);

	Gson gson = buildGson();

	Type type = new TypeToken<PetfinderResponse<GetShelterPets<Pet>>>() {
	}.getType();

	PetfinderResponse<GetShelterPets<T>> getShelterPetsResponse = gson
		.fromJson(response, type);

	getShelterPetsResponse.validate();

	return getShelterPetsResponse.getPetfinder().getPets();
    }

    /**
     * 
     * @param animal
     * @param breed
     * @return
     * @throws InvalidResponseCodeException
     * @throws IOException
     * @throws JsonParseException
     * @deprecated This call recently starting returning HTTP 500 errors.
     *             Deprecating and leaving it in here in the event it's some
     *             configuration error on their end.
     */
    public Shelter[] GetShelterListByBreed(String animal, String breed)
	    throws InvalidResponseCodeException, IOException,
	    JsonParseException {
	// Generate args
	final String[] args = { "animal=" + animal, "breed=" + breed };
	final String URI = genUri(PetfinderApiMethod.GET_SHELTER_LIST_BY_BREED,
		args);

	String response = httpGET(URI);

	Gson gson = buildGson();

	Type type = new TypeToken<PetfinderResponse<GetShelterListByBreed>>() {
	}.getType();

	PetfinderResponse<GetShelterListByBreed> getShelterListByBreedResponse = gson
		.fromJson(response, type);

	getShelterListByBreedResponse.validate();

	getShelterListByBreedResponse.getPetfinder().getShelterList();

	// getShelterListByBreedResponse = gson
	// .fromJson(response, GetShelterListByBreedResponse.class);

	// getShelterListByBreedResponse.validate();
	//
	// return getShelterListByBreedResponse.getPetList();
	return null;
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
	    for (String arg : args) {
		builder.append(arg);
		builder.append("&");
	    }
	}

	builder.append("format=json");

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
	String str = "";
	BufferedReader in = null;
	try {
	    URL url = new URL(uri);

	    in = new BufferedReader(new InputStreamReader(url.openStream()));
	    StringBuilder builder = new StringBuilder();

	    while ((str = in.readLine()) != null) {
		if (debug)
		    System.out.println(str);
		builder.append(str);
	    }

	    str = builder.toString();
	} catch (MalformedURLException e) {
	    System.out.println("MalformedURLException: " + uri);
	} catch (IOException e) {
	    System.out.println("IOException trying to GET: " + uri);
	    throw e;
	} finally {
	    if (in != null)
		in.close();
	}
	return str;
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
	return lastPetResponse[index];
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

    private Gson buildGson() {
	GsonBuilder builder = new GsonBuilder();
	builder.registerTypeAdapter(StringArrayWrapper[].class,
		new StringArrayAdapter());
	builder.registerTypeAdapter(Shelter[].class, new ShelterArrayAdapter());
	return builder.create();
    }
}
