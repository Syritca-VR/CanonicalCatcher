package org.canonicalurlcatcher

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import java.io.FileOutputStream

internal class AppTest {

    @Test
    fun `getParser should read json file`() {
        val parser by lazy {
            Parser("in-test\\Stopwach.sample.json")
        }
    }

    @Test
    fun `getNextPart should get 10 urls from json file`() {
        val parser = Parser("C:\\Users\\User\\IdeaProjects\\" +
                "CanonicalUrlCatcher\\src\\test\\kotlin\\" +
                "org\\canonicalurlcatcher\\" +
                "in-test\\Stopwach.sample.json").parse()
    }

    @Test
    fun `main should create 10 coroutines`() {
        runBlocking {
            val coroutines = List(10) {
                val coroutine = async { `doWork should take link and parse it`() }
                coroutine.start()
            }
            coroutines.parallelStream()
        }
    }

    @Test
    fun `writeFile should write data to file`() {
        val path: String = "C:\\Users\\User\\IdeaProjects\\" +
                "CanonicalUrlCatcher\\src\\test\\kotlin\\" +
                "org\\canonicalurlcatcher\\" +
                "in-test\\URL's.cws"
        val data: String = ""
        FileOutputStream(path, true).bufferedWriter().use { it.write(data) }
    }

    val items: List<String> = listOf("Y8YGSw_7OSw", "FpMSlmiF9wE", "SrgVWSYKLjk", "vAKieoLMEAw",
            "Tzcu4lDrdvg", "Vw_usen7qK8", "GSkDYuBkJ3o", "-2Kfu0AgRbk", "Kz2xpeGkcRU", "NpZc1cWQ3kY")

    @Test
    fun `doWork should take link and parse it`() {
            val links = `getNextPart should get 10 urls from json file`()
                canonicalUrlParse(items)
    }

    @Test
    fun `canonicalUrlParse should parse url, get canonical url and write it to file`() {
        val urlPrefix = "https://www.youtube.com/watch?v="
        val file = "URL's.cws"

        try {
            for (url in items) {
                val doc = Jsoup.connect("$urlPrefix$url").get()
                val elements = doc.select("link[rel='canonical']")
                if (elements.isEmpty()) {
                    writeFile(file, "$urlPrefix$url\n\r")
                } else {
                    val res = elements.first().attr("href")
                    if (res == "$urlPrefix$url" && res.length <= "$urlPrefix$url".length) {
                        writeFile(file, "$res\n\r")
                    } else {
                        writeFile(file, "$urlPrefix$url, $res\n\r")
                    }
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}