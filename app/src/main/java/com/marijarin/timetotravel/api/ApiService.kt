package com.marijarin.timetotravel.api

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ApiService @Inject constructor(
    private val client: OkHttpClient,
) {
    fun getFlights(): String {
        val startLocationCodeRequest = "{\"startLocationCode\":\"LED\"}"
        val body =
            startLocationCodeRequest.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val request = Request.Builder()
            .url(ApiModule.BASE_URL)
            .addHeader("authority", "vmeste.wildberries.ru")
            .addHeader("accept", "application/json, text/plain, */*")
            .addHeader("cache-control", "no-cache")
            .addHeader("content-type", "application/json")
            .addHeader("origin", "https://vmeste.wildberries.ru")
            .addHeader("pragma", "no-cache")
            .addHeader("referer", "https://vmeste.wildberries.ru/avia")
            .addHeader("sec-fetch-dest", "empty")
            .addHeader("sec-fetch-mode", "cors")
            .addHeader("sec-fetch-site", "same-origin")
            .post(body)
            .build()
        try {
            val response = client.newCall(request).execute()
            return response.body?.string() ?: ""
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }

}
