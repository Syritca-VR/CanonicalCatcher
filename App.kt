@file:JvmName("App")
package org.canonicalurlcatcher

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.*
import java.io.*



val parser by lazy {
    Parser("in\\fulldive-stopwatches-production.statistic.json")
}


fun getNextPart(): List<String> {
    return parser.parse()
}

fun main() = runBlocking{
    val doCoroutinesWork = List(10){
        launch {
            val coroutine = async {
                doWork()
            }
        }
    }
}


fun writeFile(path: String, data: String) {
    FileOutputStream(path, true).bufferedWriter().use { it.write(data) }
}


fun doWork() {

      do {
          val links = getNextPart()
          if (links.isNotEmpty()) {
              canonicalUrlParse(links)
          }
      } while (links.isNotEmpty())

}



fun canonicalUrlParse(items: List<String>) {

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

