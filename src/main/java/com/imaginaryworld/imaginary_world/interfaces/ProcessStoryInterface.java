package com.imaginaryworld.imaginary_world.interfaces;

import io.appwrite.models.Document;

import java.util.Collection;

public interface ProcessStoryInterface {
    public void processStory(Collection<Document<Object>> stories);
    public void processStory(Document<Object> document);

}
