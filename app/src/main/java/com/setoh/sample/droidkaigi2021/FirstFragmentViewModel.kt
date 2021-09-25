package com.setoh.sample.droidkaigi2021

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragmentViewModel(
    application: Application,
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AndroidViewModel(application) {

    private val _textviewFirstText: MutableLiveData<String> = MutableLiveData()
    val textviewFirstText: LiveData<String> = _textviewFirstText

    init {
        _textviewFirstText.value = application.getString(R.string.hello_first_fragment)
    }

    fun onButtonFirstClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val url = "https://google.com"
        _textviewFirstText.value = getApplication<Application>().getString(R.string.loading)
        viewModelScope.launch {
            _textviewFirstText.value = withContext(dispatcher) {
                repository.blockingGetResponseCode(url).toString()
            }
        }
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