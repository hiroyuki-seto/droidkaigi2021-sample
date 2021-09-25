package com.setoh.sample.droidkaigi2021

import android.content.Context
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import org.robolectric.shadows.ShadowApplication
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.LEGACY)
@Config(sdk = [29])
class FirstFragmentViewModelTest {

    private lateinit var mockRepository: Repository
    private lateinit var viewModel: FirstFragmentViewModel

    private lateinit var loadingText: String

    @Before
    fun setup() {
        mockRepository = mockk()
        viewModel = FirstFragmentViewModel(
            application = ApplicationProvider.getApplicationContext(),
            repository = mockRepository,
        )

        loadingText = ApplicationProvider.getApplicationContext<Context>()
            .getString(R.string.loading)
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
        viewModel.textviewFirstText.observeForever(spyObserver)
        viewModel.onButtonFirstClick(mockk())

        ShadowApplication.runBackgroundTasks()
        ShadowLooper.runUiThreadTasks()

        assertThat(viewModel.textviewFirstText.value).isEqualTo(expectedResponseCode.toString())
        verifyOrder {
            spyObserver.onChanged(loadingText)
            spyObserver.onChanged(expectedResponseCode.toString())
        }
    }
}