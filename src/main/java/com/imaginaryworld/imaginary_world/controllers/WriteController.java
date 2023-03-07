package com.imaginaryworld.imaginary_world.controllers;

import com.imaginaryworld.imaginary_world.Utilities;
import com.imaginaryworld.imaginary_world.appwrite.Helper;
import com.imaginaryworld.imaginary_world.interfaces.AppwriteInitCallback;
import com.imaginaryworld.imaginary_world.interfaces.WriteCallback;
import com.imaginaryworld.imaginary_world.models.Chapter;
import com.imaginaryworld.imaginary_world.models.Connection;
import com.imaginaryworld.imaginary_world.models.Story;
import com.imaginaryworld.imaginary_world.models.Subscriber;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WriteController implements ErrorController, WriteCallback, AppwriteInitCallback {
    @Autowired
    Helper helper;
    @Autowired
    Utilities utilities;

    HttpStatus status;
    String message;
    
    @PostMapping(value = "/chapter")
    ResponseEntity<HttpStatus> chapter(@RequestBody Chapter chapter){
        chapter = utilities.processIncomingChapter(chapter);
        helper.writeChapter(this, chapter);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/story")
    ResponseEntity<HttpStatus> story(@RequestBody Story story){
        helper.writeStory(this, story);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping(value = "/connection")
    ResponseEntity<String> connection(@RequestBody Connection connection){
        connection = utilities.processIncomingConnection(connection);
        helper.writeConnection(this, connection);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping(value = "/subscribe")
    ResponseEntity<String> subscribe(@RequestBody Subscriber subscriber){
        helper.writeSubscriber(this, subscriber);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public synchronized void success(boolean success, String message) {
        if(success)
            status = HttpStatus.CREATED;
        else
            status = HttpStatus.BAD_REQUEST;
        this.message = message;
        notify();
    }

    @Override
    public void initialised(boolean initialised) {
        if(!initialised)
            notifyAll();
    }
}
