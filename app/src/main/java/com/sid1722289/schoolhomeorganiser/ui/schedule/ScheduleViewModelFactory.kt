package com.sid1722289.schoolhomeorganiser.ui.schedule

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabaseDao
import com.sid1722289.schoolhomeorganiser.ui.settings.SettingsViewModel
import java.lang.IllegalArgumentException

class ScheduleViewModelFactory(
        private val dataSource: ScheduleDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
