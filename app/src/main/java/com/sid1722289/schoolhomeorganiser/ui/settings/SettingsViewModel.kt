package com.sid1722289.schoolhomeorganiser.ui.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid1722289.schoolhomeorganiser.database.DayData
import com.sid1722289.schoolhomeorganiser.database.DayDatabaseDao
import com.sid1722289.schoolhomeorganiser.database.LessonData
import com.sid1722289.schoolhomeorganiser.database.LessonDatabaseDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsViewModel(val database: DayDatabaseDao,
                        application: Application) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Home Schooling"
    }
    val text: LiveData<String> = _text

    fun addDayData(day: String, startTime: String, finishTime: String){
        viewModelScope.launch {
            Log.d("SVM - Method", "addDayData")
            addDayDataToDatabase(day, startTime, finishTime).await()
        }
        Thread.sleep(300)
    }
    private fun addDayDataToDatabase(day: String, startTime: String, finishTime: String) = GlobalScope.async {
        var dayDetails: DayData? = database.get(day)
        if(dayDetails == null) {
            Log.d("SVM - dayDetails", "= $dayDetails")
            initializeDatabase()
            dayDetails = database.getLastDayDetails()
            if(dayDetails == null)
            {
                Log.d("SVM - dayDetails", "= STILL null")
            }
            else
            {
                Log.d("SVM - dayDetails", "lesson name of something from the db " + dayDetails.Day)
            }
        }
        else{
            database.updateDay(day, startTime, finishTime)
        }
    }
    private fun initializeDatabase() {
        viewModelScope.launch {
            clear()
            val monday = DayData()
            val tuesday = DayData()
            val wednesday = DayData()
            val thursday = DayData()
            val friday = DayData()
            monday.Day = "Monday"
            tuesday.Day = "Tuesday"
            wednesday.Day = "Wednesday"
            thursday.Day = "Thursday"
            friday.Day = "Friday"
            insert(monday)
            insert(tuesday)
            insert(wednesday)
            insert(thursday)
            insert(friday)
        }
    }
    private suspend fun clear() {
        database.clear()
    }
    private suspend fun insert(dayData: DayData) {
        database.insert(dayData)
    }
}




