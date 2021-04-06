package com.sid1722289.schoolhomeorganiser.ui.lessonsettings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.database.DayDatabaseDao
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabaseDao
import java.lang.IllegalArgumentException

class LessonSettingsViewModelFactory(
        private val dayDataSource: DayDatabaseDao,
        private val scheduleDataSource: ScheduleDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LessonSettingsViewModel::class.java)) {
            return LessonSettingsViewModel(dayDataSource, scheduleDataSource,  application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
