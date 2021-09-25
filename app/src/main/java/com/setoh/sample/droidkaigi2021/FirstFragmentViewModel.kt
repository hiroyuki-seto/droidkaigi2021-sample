package com.setoh.sample.droidkaigi2021

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FirstFragmentViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _responseCode: MutableLiveData<String> = MutableLiveData()
    val responseCode: LiveData<String> = _responseCode

    fun loadResponseCode(url: String) {
        object : AsyncTask<Unit, Unit, String>() {
            override fun doInBackground(vararg p0: Unit?): String =
                repository.blockingGetResponseCode(url).toString()

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                _responseCode.value = result
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