package com.setoh.sample.droidkaigi2021

import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import io.mockk.*
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

        // see https://stackoverflow.com/questions/67736717/mockk-spky-throwing-noclassdeffounderror
        @Suppress("ObjectLiteralToLambda")
        val observer: Observer<String> = object : Observer<String> {
            override fun onChanged(t: String?) {}
        }
        val spyObserver = spyk(observer)
        viewModel.responseCode.observeForever(spyObserver)
        viewModel.loadResponseCode("https://hogehoge.com")

        ShadowApplication.runBackgroundTasks()
        ShadowLooper.runUiThreadTasks()

        assertThat(viewModel.responseCode.value).isEqualTo(expectedResponseCode.toString())
        verify(exactly = 1) {
            spyObserver.onChanged(expectedResponseCode.toString())
        }
    }
}