package com.sid1722289.schoolhomeorganiser.ui.lesson

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.database.LessonDatabaseDao
import java.lang.IllegalArgumentException


class LessonViewModelFactory(
    private val dataSource: LessonDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(LessonViewModel::class.java)) {
                return LessonViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
