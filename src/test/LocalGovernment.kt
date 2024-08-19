package org.example.horizonpay

import com.google.common.hash.Hashing
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

private val time = System.currentTimeMillis()

fun main() {
    apiRequest()
}

private fun apiRequest() {
    val sign = ENV.getSign(time, ENV.CHANNEL_ID, ENV.SECRET_KEY)

    val request = Request.Builder()
        .url(String.format(ENV.URL + "api/coopvest/lga?id=%s&channelId=%s&time=%s&sign=%s", 1, ENV.CHANNEL_ID, time, sign))
        .addHeader("X-Tenantid", "9rapoint")
        .get()
        .build()

    val client = OkHttpClient().newBuilder()
        .connectTimeout(300, TimeUnit.SECONDS)
        .readTimeout(300, TimeUnit.SECONDS)
        .writeTimeout(300, TimeUnit.SECONDS)
        .build()

    val response = client.newCall(request).execute()

    handleResponse(response.body?.string() ?: "")
    println(String.format(ENV.URL + "api/coopvest/lga?id=%s&channelId=%s&time=%s&sign=%s", 1, ENV.CHANNEL_ID, time, sign))
}

private fun handleResponse(response: String) {
    println(response)
    val gson = Gson().newBuilder().setPrettyPrinting().create()
    val jsonResponseBody: JsonObject = gson.fromJson(response, JsonObject::class.java)
    if (jsonResponseBody.has("data")) {
        val data = jsonResponseBody.get("data")
        val lgas = gson.fromJson(data, JsonArray::class.java)
        println(data)
        for (lga in lgas) {
            println(lga)
        }
    }
}