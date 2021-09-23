package com.setoh.sample.droidkaigi2021

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowApplication
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
class FirstFragmentViewModelTest {

    private lateinit var mockRepository: Repository
    private lateinit var viewModel: FirstFragmentViewModel

    @Before
    fun setup() {
        mockRepository = mockk()
        viewModel = FirstFragmentViewModel(
            repository = mockRepository
        )
    }

    @Test
    fun test_blockingGetResponseCode_success() {
        val expectedResponseCode = 200
        every { mockRepository.blockingGetResponseCode(any()) } returns expectedResponseCode

        var code: String? = null
        viewModel.loadResponseCode("https://hogehoge.com") { responseCode ->
            code = responseCode
        }

        ShadowApplication.runBackgroundTasks()
        ShadowLooper.runUiThreadTasks()

        assertThat(code).isEqualTo(expectedResponseCode.toString())
    }
}