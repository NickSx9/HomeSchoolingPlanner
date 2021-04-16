package com.sid1722289.schoolhomeorganiser.ui.lessonsettings

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid1722289.schoolhomeorganiser.database.DayData
import com.sid1722289.schoolhomeorganiser.database.DayDatabaseDao
import com.sid1722289.schoolhomeorganiser.database.ScheduleData
import com.sid1722289.schoolhomeorganiser.database.ScheduleDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

class LessonSettingsViewModel(val dayDatabase: DayDatabaseDao,
                              val scheduleDatabase: ScheduleDatabaseDao,
                              application: Application)  : ViewModel() {
    private val _text = MutableLiveData<String>().apply {

        value = "Home Schooling"
    }
    private var _hasDataInDatabase: Boolean = false
    lateinit var _dayDataList: List<DayData>

    val text: LiveData<String> = _text

    fun checkDatabase(): Boolean {
        viewModelScope.launch() {
            doesDatabaseHaveData().await()
        }
        Thread.sleep(300)
        return _hasDataInDatabase
    }

    private suspend fun checkScheduleDatabase(day: String): Boolean {
        var hasData: Boolean = false
        var data: DayData? = dayDatabase.get(day)
        if (data != null) {
            hasData = true
        }
        return hasData
    }

    private fun doesDatabaseHaveData() = GlobalScope.async {
        var data: List<DayData> = dayDatabase.getAllRecords()
        if (data.isNotEmpty()) {
            _hasDataInDatabase = true
            _dayDataList = data
        }
    }
   fun getDayDataList(): List<DayData>{
    return _dayDataList
   }
    fun saveScheduleData(day: String, lesson:String, start:String, finish:String){
        viewModelScope.launch {
            saveData(day,lesson,start,finish)
        }
    }
    private suspend fun saveData(day: String, lesson:String, start:String, finish:String){
        var insertData: ScheduleData = ScheduleData(lessonName = lesson, Day = day, LessonStartTime = start, LessonFinishTime = finish)
        scheduleDatabase.insert(insertData)
    }
}

