package com.sid1722289.schoolhomeorganiser.ui.schedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.sid1722289.schoolhomeorganiser.database.DayDatabaseDao
import com.sid1722289.schoolhomeorganiser.database.ScheduleData
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabaseDao
import com.sid1722289.schoolhomeorganiser.formatSchedule
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ScheduleViewModel(val database: ScheduleDatabaseDao,
                        application: Application) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home Schooling"
    }
    val text: LiveData<String> = _text

    var scheduleData: List<ScheduleData> = emptyList()


    fun getDataFromDatabase(day: String) {
        viewModelScope.launch {
            getData(day).await()
        }
        Thread.sleep(3000)
    }
    private fun getData(day: String)  = GlobalScope.async {
        scheduleData = database.getDataList(day)
    }

    var listOfRecords =  formatSchedule(scheduleData, application.resources)
}
/*fun addDayData(day: String, startTime: String, finishTime: String){
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
    }*/
