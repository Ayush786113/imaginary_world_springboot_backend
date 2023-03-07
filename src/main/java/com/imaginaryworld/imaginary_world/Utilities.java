package com.imaginaryworld.imaginary_world;

import com.imaginaryworld.imaginary_world.models.Chapter;
import com.imaginaryworld.imaginary_world.models.Connection;
import io.appwrite.models.Document;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Utilities {
    public Chapter processIncomingChapter(Chapter chapter){
        new StringTokenizer(chapter.getContent().get(0)).asIterator().forEachRemaining(token -> {
            chapter.getContent().add((String) token);
        });
        chapter.getContent().remove(0);
        return chapter;
    }

    public Collection<Map<String, Object>> processOutgoingChapterMetadata(Collection<Document<Object>> documents){
        Map<String, Object> map = new HashMap<String, Object>();
        Collection<Map<String, Object>> chaptersMetadata = new LinkedList<Map<String, Object>>();
        for(Document<Object> document : documents){
            map = (Map<String, Object>) document.getData();
            filterOutgoingData(map);
            map.remove("content");
            chaptersMetadata.add(map);
        }
        return chaptersMetadata;
    }

    public Collection<Map<String, Object>> processOutgoingStory(Collection<Document<Object>> stories){
        Map<String, Object> map = new HashMap<String, Object>();
        Collection<Map<String, Object>> storiesList = new LinkedList<Map<String, Object>>();
        for(Document<Object> document : stories){
            map = (Map<String, Object>) document.getData();
            filterOutgoingData(map);
            map.remove("chapters");
            storiesList.add(map);
        }
        return storiesList;
    }
    public Map<String, Object> processOutgoingStory(Document<Object> story){
        Map<String, Object> map = new HashMap<String, Object>();
        map = (Map<String, Object>) story.getData();
        filterOutgoingData(map);
        return map;
    }

    public Map<String, Object> processOutgoingChapter(Document<Object> document){
        Map<String, Object> map = new HashMap<String, Object>();
        map = (Map<String, Object>) document.getData();
        filterOutgoingData(map);
        List<String> words = (List<String>) map.get("content");
        String content = processOutgoingStringContent(words);
        map.put("content", content);
        return map;
    }
    public Connection processIncomingConnection(Connection connection){
        String message = ((List<String>) connection.getMessage()).get(0);
        StringTokenizer stringTokenizer = new StringTokenizer(message);
        while(stringTokenizer.hasMoreTokens()){
            connection.getMessage().add(stringTokenizer.nextToken());
        }
        connection.getMessage().remove(0);
        return connection;
    }
    public Collection<Map<String, Object>> processOutgoingConnections(Collection<Document<Object>> connections){
        Collection<Map<String, Object>> connectionsList = new LinkedList<Map<String, Object>>();
        Map<String, Object> map;
        for(Document<Object> connection: connections){
            map = (Map<String, Object>) connection.getData();
            boolean _public = (boolean) map.get("public");
            if(!_public)
                continue;
            filterOutgoingData(map);
            map.remove("$id");
            List<String> words = (List<String>) map.get("message");
            String message = processOutgoingStringContent(words);
            map.put("message", message);
            connectionsList.add(map);
        }
        return connectionsList;
    }
    private void filterOutgoingData(Map<String, Object> map){
        map.remove("$createdAt");
        map.remove("$updatedAt");
        map.remove("$permissions");
        map.remove("$collectionId");
        map.remove("$databaseId");
    }
    private String processOutgoingStringContent(List<String> words){
        StringBuilder stringBuilder = new StringBuilder();
        for(String token : words){
            stringBuilder.append(token);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString().trim();
    }
}
