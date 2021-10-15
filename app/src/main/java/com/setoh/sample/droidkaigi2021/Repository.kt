package com.setoh.sample.droidkaigi2021

open class Repository(
    private val remoteDataSource: RemoteDataSource = RemoteDataSource()
) {
    open fun blockingGetResponseCode(url: String): Int =
        remoteDataSource.blockingGetResponseCode(url)
}