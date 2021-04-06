package com.sid1722289.schoolhomeorganiser.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.database.DayDatabaseDao
import java.lang.IllegalArgumentException

class SettingViewModelFactory(
        private val dataSource: DayDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}