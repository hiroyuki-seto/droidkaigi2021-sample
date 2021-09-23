package com.setoh.sample.droidkaigi2021

class Repository(
    private val remoteDataSource: RemoteDataSource = RemoteDataSource()
) {
    fun blockingGetResponseCode(url: String): Int =
        remoteDataSource.blockingGetResponseCode(url)
}