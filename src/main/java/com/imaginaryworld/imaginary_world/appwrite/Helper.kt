package com.imaginaryworld.imaginary_world.appwrite

import com.imaginaryworld.imaginary_world.interfaces.ProcessChapterInterface
import com.imaginaryworld.imaginary_world.interfaces.ProcessConnectionInterface
import com.imaginaryworld.imaginary_world.interfaces.ProcessStoryInterface
import com.imaginaryworld.imaginary_world.interfaces.WriteCallback
import com.imaginaryworld.imaginary_world.models.Chapter
import com.imaginaryworld.imaginary_world.models.Connection
import com.imaginaryworld.imaginary_world.models.Story
import com.imaginaryworld.imaginary_world.models.Subscriber
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component

@Component
class Helper {
    private val database: Database = Database()

    @OptIn(DelicateCoroutinesApi::class)
    fun writeChapter(writeCallback: WriteCallback, chapter: Chapter) {
        GlobalScope.launch {  database.writeChapter(writeCallback, chapter) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun writeStory(writeCallback: WriteCallback, story: Story){
        GlobalScope.launch { database.writeStory(writeCallback, story) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun writeConnection(writeCallback: WriteCallback, connection: Connection){
        GlobalScope.launch { database.writeConnection(writeCallback, connection) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun writeSubscriber(writeCallback: WriteCallback, subscriber: Subscriber){
        GlobalScope.launch { database.writeSubscriber(writeCallback, subscriber) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun readChapterMetadata(processChapterInterface: ProcessChapterInterface, chapters: Array<String>){
        GlobalScope.launch { database.readChaptersMetadata(processChapterInterface, chapters) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun readStories(processStoryInterface: ProcessStoryInterface){
        GlobalScope.launch { database.readStory(processStoryInterface) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun readStory(processStoryInterface: ProcessStoryInterface, storyID: String){
        GlobalScope.launch { database.readStory(processStoryInterface, storyID) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun readChapter(processChapterInterface: ProcessChapterInterface, chapterID: String) {
        GlobalScope.launch { database.readChapter(processChapterInterface, chapterID) }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun readConnections(processConnectionInterface: ProcessConnectionInterface) {
        GlobalScope.launch { database.readConnection(processConnectionInterface) }
    }
}