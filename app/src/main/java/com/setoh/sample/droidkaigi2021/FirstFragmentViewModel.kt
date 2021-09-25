package com.setoh.sample.droidkaigi2021

import android.app.Application
import android.os.AsyncTask
import android.view.View
import androidx.lifecycle.*

class FirstFragmentViewModel(
    application: Application,
    private val repository: Repository,
) : AndroidViewModel(application) {

    private val _textviewFirstText: MutableLiveData<String> = MutableLiveData()
    val textviewFirstText: LiveData<String> = _textviewFirstText

    init {
        _textviewFirstText.value = application.getString(R.string.hello_first_fragment)
    }

    fun onButtonFirstClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val url = "https://google.com"
        _textviewFirstText.value = getApplication<Application>().getString(R.string.loading)
        object : AsyncTask<Unit, Unit, String>() {
            override fun doInBackground(vararg p0: Unit?): String =
                repository.blockingGetResponseCode(url).toString()

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                _textviewFirstText.value = result
            }
        }.execute(Unit)
    }

    class Factory(
        private val application: Application,
        private val repository: Repository,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass == FirstFragmentViewModel::class.java) {
                FirstFragmentViewModel(
                    application,
                    repository
                ) as T
            } else {
                throw IllegalArgumentException()
            }
        }

    }
}