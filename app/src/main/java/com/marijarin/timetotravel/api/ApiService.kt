package com.marijarin.timetotravel.api

import kotlinx.coroutines.delay
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
