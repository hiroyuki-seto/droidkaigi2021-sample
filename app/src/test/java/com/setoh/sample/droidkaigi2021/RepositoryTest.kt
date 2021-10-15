package com.setoh.sample.droidkaigi2021

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    private lateinit var mockRemoteDataSource: RemoteDataSource
    private lateinit var repository: Repository

    @Before
    fun setup() {
        mockRemoteDataSource = mockk()
        repository = Repository(mockRemoteDataSource)
    }

    @Test
    fun test_blockingGetResponseCode_success() {
        val expectedResponseCode = 200
        every { mockRemoteDataSource.blockingGetResponseCode(any()) } returns expectedResponseCode

        val result = repository.blockingGetResponseCode("https://hogehoge.com")

        assertThat(result).isEqualTo(expectedResponseCode)
    }
}