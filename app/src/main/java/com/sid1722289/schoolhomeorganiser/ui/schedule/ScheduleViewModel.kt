package com.sid1722289.schoolhomeorganiser.ui.schedule

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sid1722289.schoolhomeorganiser.database.DayDatabaseDao
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabaseDao

class ScheduleViewModel(val database: ScheduleDatabaseDao,
                        application: Application) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home Schooling"
    }
    val text: LiveData<String> = _text
}

