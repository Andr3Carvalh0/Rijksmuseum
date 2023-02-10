package pt.andre.rijksmuseum.data.utilities

import okhttp3.mockwebserver.MockResponse
import java.io.ByteArrayOutputStream

private const val BUFFER_SIZE = 1024
private const val SUCCESS_RESPONSE_CODE = 200

fun errorResponse(code: Int): MockResponse {
    return MockResponse().apply {
        setResponseCode(code)
        setBody("{}")
        addHeader("Content-type: application/json")
    }
}

fun Any.successResponseFromFile(path: String): MockResponse {
    val body = readFromFile(path)
    return MockResponse().apply {
        setResponseCode(SUCCESS_RESPONSE_CODE)
        setBody(body)
        addHeader("Content-type: application/json")
    }
}

private fun Any.readFromFile(file: String): String {
    val inputStream = this.javaClass.classLoader?.getResourceAsStream(file)
    val result = ByteArrayOutputStream()
    val buffer = ByteArray(BUFFER_SIZE)
    var length = inputStream?.read(buffer) ?: -1

    while (length != -1) {
        result.write(buffer, 0, length)
        length = inputStream?.read(buffer) ?: -1
    }

    return result.toString("UTF-8")
}
