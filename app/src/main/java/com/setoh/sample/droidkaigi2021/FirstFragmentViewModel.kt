package com.setoh.sample.droidkaigi2021

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirstFragmentViewModel(
    private val repository: Repository
) : ViewModel() {

    fun loadResponseCode(url: String, callback: (String?) -> Unit) {
        object : AsyncTask<Unit, Unit, String>() {
            override fun doInBackground(vararg p0: Unit?): String =
                repository.blockingGetResponseCode(url).toString()

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                callback(result)
            }
        }.execute(Unit)
    }

    class Factory(
        private val repository: Repository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass == FirstFragmentViewModel::class.java) {
                FirstFragmentViewModel(repository) as T
            } else {
                throw IllegalArgumentException()
            }
        }

    }
}