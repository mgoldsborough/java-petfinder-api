Petfinder API for Java
======================

Simple Java library for integrating with the Petfiner API (http://www.petfinder.com/developers/api-docs).  
Uses of Gson library (http://code.google.com/p/google-gson/) to serialize JSON responses. 

Installation
------------
1. Go here http://www.petfinder.com/developers/api-key, create an account and get an API key/secret.
2. Build/add this project to your own.
3. Create a PetfinderAPI object using your API key/secret and invoke methods. Example:

```
PetfinderApi api = new PetfinderApi(<key>, <YOUR_SECRET>);
Pet pet = api.GetPet(123456);
```

Notes
-----
- If you are testing the library itself, you'll need to set the API_KEY/API_SECRET values in the PetfinderApi.properties. 
- Built against GSON v2.2.1.

This library is still a work in progress...
