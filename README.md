Petfinder API for Java
======================

Simple Java library for integrating with the Petfiner API.

Installation
------------
1. Go here http://www.petfinder.com/developers/api-key, create an account and get an API key/secret.
2. Build/add this project to your own.
3. Create a PetfinderAPI object using your API key/secret and invoke methods. Example:

'''java
PetfinderApi api = new PetfinderApi(<key>, <YOUR_SECRET>);
Pet pet = api.GetPet(123456);
'''

Notes
-----
- If you are testing the library itself, you'll need to set the API_KEY/API_SECRET values in the PetfinderApi.properties. 
- We use SimpleXML v2.6.2 (http://simple.sourceforge.net/) to serialize responses.  You'll need to download it and add it to your build path.
- Petfinder docs: http://www.petfinder.com/developers/api-docs

This library is still a work in progress...
