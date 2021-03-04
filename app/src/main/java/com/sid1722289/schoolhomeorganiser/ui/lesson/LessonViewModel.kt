package com.sid1722289.schoolhomeorganiser.ui.lesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LessonViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is meal planner Fragment"
    }
    val text: LiveData<String> = _text
}
