package com.imaginaryworld.imaginary_world.controllers;

import com.imaginaryworld.imaginary_world.Utilities;
import com.imaginaryworld.imaginary_world.appwrite.Helper;
import com.imaginaryworld.imaginary_world.interfaces.AppwriteInitCallback;
import com.imaginaryworld.imaginary_world.interfaces.ProcessChapterInterface;
import com.imaginaryworld.imaginary_world.interfaces.ProcessConnectionInterface;
import com.imaginaryworld.imaginary_world.interfaces.ProcessStoryInterface;
import io.appwrite.models.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
public class ReadController implements ErrorController, ProcessChapterInterface, ProcessStoryInterface, ProcessConnectionInterface, AppwriteInitCallback {
    @Autowired
    Helper helper;
    @Autowired
    Utilities utilities;

    Collection<Map<String, Object>> chapters, stories, connections;
    Map<String, Object> story, chapter;
    String content;

    @GetMapping(value = "/")
    String home(){
        return "Building the Imaginary World";
    }

    @GetMapping(value = "/chapters", params = "chaptersid")
    synchronized Collection<Map<String, Object>> chapter(@RequestParam String [] chaptersid){
        helper.readChapterMetadata(this, chaptersid);
        try { wait(); } catch ( Exception e ) { System.out.println( e.getMessage() ); }
        return chapters;
    }

    @GetMapping(value = "/chapter", params = "chapterid")
    synchronized Map<String, Object> chapter(@RequestParam String chapterid){
        helper.readChapter(this, chapterid);
        try { wait(); } catch ( Exception e ) { System.out.println( e.getMessage() ); }
        return chapter;
    }

    @GetMapping(value = "/stories")
    synchronized Collection<Map<String, Object>> stories(){
        helper.readStories(this);
        try { wait(); } catch( Exception e ) { System.out.println(e.getMessage()); }
        return stories;
    }
    @GetMapping(value = "/story", params = "storyid")
    synchronized Map<String, Object> story(@RequestParam String storyid){
        helper.readStory(this, storyid);
        try { wait(); } catch( Exception e ) { System.out.println(e.getMessage()); }
        return story;
    }
    @GetMapping(value = "/connections")
    synchronized Collection<Map<String, Object>> connections(){
        helper.readConnections(this);
        try { wait(); } catch( Exception e ) { System.out.println(e.getMessage()); }
        return connections;
    }

    @Override
    public synchronized void processChapterInformation(Collection<Document<Object>> chapters) {
        this.chapters = utilities.processOutgoingChapterMetadata(chapters);
        notify();
    }
    @Override
    public synchronized void processChapter(Document<Object> chapter) {
        this.chapter = utilities.processOutgoingChapter(chapter);
        notify();
    }

    @Override
    public synchronized void processStory(Collection<Document<Object>> stories) {
        this.stories = utilities.processOutgoingStory(stories);
        notify();
    }

    @Override
    public synchronized void processStory(Document<Object> story) {
        this.story = utilities.processOutgoingStory(story);
        notify();
    }

    @Override
    public synchronized void processOutgoingConnection(Collection<Document<Object>> connections) {
        this.connections = utilities.processOutgoingConnections(connections);
        notify();
    }

    @Override
    public synchronized void initialised(boolean initialised) {
        if(!initialised)
            notifyAll();
    }
}
