@file:JvmName("JsonParser")
package org.canonicalurlcatcher

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.squareup.moshi.*
import java.io.Closeable
import java.io.File

data class ResourceData(
        var url: String)


class Parser(fileName: String) : Closeable {
    private var reader: JsonReader = Gson().newJsonReader(File(fileName).bufferedReader())

    init {
        reader.beginArray()
    }

    val YOUTUBE_PREFIX = "youtube:"

    fun parse(): List<String> {
        val result = ArrayList<String>(100)

        var i = 0
        while (reader.hasNext() && i < 100) {
            var url = ""
            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "targetId" -> url = reader.nextString()
                    else -> reader.skipValue()
                }
            }
            reader.endObject()
            if (url == "") {
                throw JsonDataException("Missing required field")
            }
            val resourceData = ResourceData(url).url
            result.add(resourceData)
            ++i
        }
        return result
                .filter {
                    it.startsWith(YOUTUBE_PREFIX)
                }
                .map {
                    it.substring(YOUTUBE_PREFIX.length)
                }
    }

    override fun close() {
        reader.endArray()
        reader.close()

    }

}