import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.URL
import kotlin.math.absoluteValue

fun main() {
    try {

            try {
                val connections = Jsoup.connect("https://vk.com/login")
                val doc = connections.get()
                val request = connections.response().statusCode()
                if (request == 302 || request == 301) {
                    println(request)
                }

                println(request)
                //   println("redirect")


            } catch (e: Exception) {
                writeFile("URL's.cws", "${e.message}\n\r")
            }

    } catch (e: Exception) {
        println(e.message)
    }
}