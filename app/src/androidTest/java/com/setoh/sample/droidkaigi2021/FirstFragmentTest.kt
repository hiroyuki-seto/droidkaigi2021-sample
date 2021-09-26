package com.setoh.sample.droidkaigi2021

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

private const val MOCK_RESPONSE_CODE = 200

@RunWith(AndroidJUnit4::class)
class FirstFragmentTest {

    @Before
    fun setup() {
        RepositoryProvider.repository = MockRepository()
    }

    @After
    fun tearDown() {
        RepositoryProvider.reset()
    }

    @Test
    fun initialStatus() {
        launchFragmentInContainer<FirstFragment>()
        onView(withId(R.id.textview_first)).check(matches(withText(R.string.hello_first_fragment)))
    }

    @Test
    fun clickFirstButton_textViewDisplaysResponseCode() {
        launchFragmentInContainer<FirstFragment>()
        onView(withId(R.id.button_first)).perform(ViewActions.click())
        onView(withId(R.id.textview_first)).check(matches(withText(MOCK_RESPONSE_CODE.toString())))
    }

    private class MockRepository : Repository() {
        override fun blockingGetResponseCode(url: String): Int = MOCK_RESPONSE_CODE
    }
}