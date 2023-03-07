package com.imaginaryworld.imaginary_world.appwrite

import com.imaginaryworld.imaginary_world.interfaces.ProcessChapterInterface
import com.imaginaryworld.imaginary_world.interfaces.ProcessConnectionInterface
import com.imaginaryworld.imaginary_world.interfaces.ProcessStoryInterface
import com.imaginaryworld.imaginary_world.interfaces.WriteCallback
import com.imaginaryworld.imaginary_world.models.Chapter
import com.imaginaryworld.imaginary_world.models.Connection
import com.imaginaryworld.imaginary_world.models.Story
import com.imaginaryworld.imaginary_world.models.Subscriber
import io.appwrite.Client
import io.appwrite.ID
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.services.Databases
import java.util.LinkedList

class Database {
    private lateinit var client: Client
    private lateinit var database: Databases

    init {
        val init: Init = Init()
        client = Constants.CLIENT;
        database = Databases(client)
    }

    suspend fun writeChapter(writeCallback: WriteCallback, chapter: Chapter){
        try{
            val data: Document<Any> = database.createDocument(
                Constants.DATABASE_ID,
                Constants.COLLECTION_CHAPTERS_ID,
                ID.unique(),
                chapter
            )
            writeCallback.success(true, data.id)
        }
        catch (exception: Exception){
            writeCallback.success(false, exception.message)
        }
    }

    suspend fun writeStory(writeCallback: WriteCallback, story: Story){
        try{
            val data: Document<Any> = database.createDocument(
                Constants.DATABASE_ID,
                Constants.COLLECTION_STORIES_ID,
                ID.unique(),
                story
            )
            writeCallback.success(true, data.id)
        }
        catch(exception: Exception){
            writeCallback.success(false, exception.message)
        }
    }

    suspend fun writeConnection(writeCallbacks: WriteCallback, connection: Connection){
        try{
            val data: Document<Any> = database.createDocument(
                Constants.DATABASE_ID,
                Constants.COLLECTION_CONNECTION_ID,
                ID.unique(),
                connection
            )
            writeCallbacks.success(true, data.id)
        }
        catch(exception: Exception){
            writeCallbacks.success(false, exception.message)
        }
    }

    suspend fun writeSubscriber(writeCallbacks: WriteCallback, subscriber: Subscriber){
        try{
            val data: Document<Any> = database.createDocument(
                Constants.DATABASE_ID,
                Constants.COLLECTION_SUBSCRIBERS_ID,
                ID.unique(),
                subscriber
            )
            writeCallbacks.success(true, data.id);
        }
        catch (exception: Exception){
            writeCallbacks.success(false, exception.message);
        }
    }

    suspend fun readChaptersMetadata(processChapterInterface: ProcessChapterInterface, chapters: Array<String>) {
        var chapterData: Document<Any>
        val chaptersList: LinkedList<Document<Any>> = LinkedList<Document<Any>>()
        try{
            for(chapter in chapters){
                chapterData = database.getDocument(
                    Constants.DATABASE_ID,
                    Constants.COLLECTION_CHAPTERS_ID,
                    chapter
                )
                chaptersList.add(chapterData)
            }
            processChapterInterface.processChapterInformation(chaptersList)
        }
        catch (e: Exception){
            println("error in per chapter processing")
        }
    }

    suspend fun readChapter(processChapterInterface: ProcessChapterInterface, chapterID: String){
        try{
            val chapter: Document<Any> = database.getDocument(
                Constants.DATABASE_ID,
                Constants.COLLECTION_CHAPTERS_ID,
                chapterID
            )
            processChapterInterface.processChapter(chapter)
        }
        catch(e: Exception){
            println("error in whole chapter processing")
        }
    }

    suspend fun readStory(processStoryInterface: ProcessStoryInterface){
        try{
            val stories: DocumentList<Any> = database.listDocuments(
                Constants.DATABASE_ID,
                Constants.COLLECTION_STORIES_ID
            )
            processStoryInterface.processStory(stories.documents)
        }
        catch (e: Exception){
            println("error in reading stories")
        }
    }

    suspend fun readStory(processStoryInterface: ProcessStoryInterface, storyID: String){
        try{
            val story: Document<Any> = database.getDocument(
                Constants.DATABASE_ID,
                Constants.COLLECTION_STORIES_ID,
                storyID
            )
            processStoryInterface.processStory(story)
        }
        catch(e: Exception){
            println("error in reading story from id")
        }
    }

    suspend fun readConnection(processConnectionInterface: ProcessConnectionInterface){
        try{
            val connections: DocumentList<Any> = database.listDocuments(
                Constants.DATABASE_ID,
                Constants.COLLECTION_CONNECTION_ID
            )
            processConnectionInterface.processOutgoingConnection(connections.documents)
        }
        catch(e: Exception){
            println("error in reading connections")
        }
    }
}