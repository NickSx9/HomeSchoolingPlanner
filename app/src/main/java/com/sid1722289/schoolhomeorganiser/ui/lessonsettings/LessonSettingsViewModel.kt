package com.sid1722289.schoolhomeorganiser.ui.lessonsettings

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid1722289.schoolhomeorganiser.database.DayData
import com.sid1722289.schoolhomeorganiser.database.DayDatabaseDao
import com.sid1722289.schoolhomeorganiser.database.ScheduleData
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabaseDao
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex

class LessonSettingsViewModel(val dayDatabase: DayDatabaseDao,
                              val scheduleDatabase: ScheduleDatabaseDao,
                              application: Application)  : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Home Schooling"
    }
    val text: LiveData<String> = _text

    fun checkDatabase(day: String): Boolean {
        var hasData: Boolean = false
        viewModelScope.async() {
            if (checkScheduleDatabase(day)) {
                hasData = true
            }
        }
        return hasData
    }

    private suspend fun checkScheduleDatabase(day: String): Boolean {
        var hasData: Boolean = false
        var data: DayData? = dayDatabase.get(day)
        if (data != null) {
            hasData = true
        }
        return hasData
    }
}

