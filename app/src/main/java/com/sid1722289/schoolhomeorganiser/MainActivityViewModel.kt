package com.sid1722289.schoolhomeorganiser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sid1722289.schoolhomeorganiser.database.GPSLocation
import com.sid1722289.schoolhomeorganiser.database.LocationDatabase
import kotlinx.coroutines.launch


class MainActivityViewModel(app: Application): AndroidViewModel(app) {
    val locationDBDao = LocationDatabase.getInstance(getApplication()).locationDatabaseDao
    init{
    }
    fun insertGpsData(gpsLocation: GPSLocation) {
        viewModelScope.launch {
            insert(gpsLocation)
        }
    }
    private suspend fun insert(gpsLocation: GPSLocation){
        locationDBDao.insert(gpsLocation)
    }
}
