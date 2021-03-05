package com.sid1722289.schoolhomeorganiser.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Home Schooling"
    }
    val text: LiveData<String> = _text
}




