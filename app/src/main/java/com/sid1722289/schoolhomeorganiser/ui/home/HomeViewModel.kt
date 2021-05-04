package com.sid1722289.schoolhomeorganiser.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.sid1722289.schoolhomeorganiser.database.LocationDatabaseDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(val database: LocationDatabaseDao,
                    application: Application) : AndroidViewModel(application) {
    var long: String = ""
    var latit: String = ""

    private val _text = MutableLiveData<String>().apply {
        value = "Home Schooling"
    }
    val text: LiveData<String> = _text

    fun prepData(){
        viewModelScope.launch {
            getGPSData().await()
        }
    }

    private fun getGPSData() = GlobalScope.async {
        var data = database.getGpsLocation()
        if (data != null) {
            latit = data.Latitude
            long = data.Longitude
        }
    }
}