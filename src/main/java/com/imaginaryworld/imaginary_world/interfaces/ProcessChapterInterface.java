package com.imaginaryworld.imaginary_world.interfaces;

import com.imaginaryworld.imaginary_world.models.Chapter;
import io.appwrite.models.Document;

import java.util.Collection;
import java.util.Map;

public interface ProcessChapterInterface {
    public void processChapterInformation(Collection<Document<Object>> documents);
    public void processChapter(Document<Object> document);
}
