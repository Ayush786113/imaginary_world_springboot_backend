package com.imaginaryworld.imaginary_world.appwrite

import io.appwrite.Client

class Init {
    init {
        try{
            val client: Client = Client()
                .setEndpoint(Constants.ENDPOINT)
                .setProject(Constants.PROJECT_ID)
                .setKey(Constants.API_KEY)
            Constants.CLIENT = client
        }
        catch (exception: Exception){
            println(exception.message)
            println("initialisation problem")
        }
    }
}