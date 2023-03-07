package com.imaginaryworld.imaginary_world.interfaces;


import io.appwrite.models.Document;

import java.util.Collection;

public interface ProcessConnectionInterface {
    public void processOutgoingConnection(Collection<Document<Object>> connections);
}
