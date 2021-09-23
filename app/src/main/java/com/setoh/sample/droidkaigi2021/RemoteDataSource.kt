package com.setoh.sample.droidkaigi2021

import okhttp3.OkHttpClient
import okhttp3.Request

class RemoteDataSource(
    private val client: OkHttpClient = OkHttpClient()
) {

    fun blockingGetResponseCode(url: String): Int {
        val request: Request = Request.Builder()
            .url(url)
            .build()

        return client.newCall(request).execute()
            .use { response -> response.code }
    }
}