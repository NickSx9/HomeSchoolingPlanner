package com.sid1722289.schoolhomeorganiser.ui.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sid1722289.schoolhomeorganiser.R
import com.sid1722289.schoolhomeorganiser.database.LessonDatabase
import com.sid1722289.schoolhomeorganiser.database.LocationDatabase
import com.sid1722289.schoolhomeorganiser.ui.lesson.LessonViewModel
import com.sid1722289.schoolhomeorganiser.ui.lesson.LessonViewModelFactory
import com.sid1722289.schoolhomeorganiser.ui.lessonsettings.LessonSettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    val client = OkHttpClient()
    private val apiKey: String = "b05cca29b43d096876f818942d9da9e0"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_Home_Settings)

        val application = requireNotNull(this.activity).application
        val dataSource = LocationDatabase.getInstance(application).locationDatabaseDao
        val viewModelFactory = HomeViewModelFactory(dataSource,application)
        val homeViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(HomeViewModel::class.java)
        var weatherImage: ImageView = root.findViewById(R.id.weatherImage)
        homeViewModel.prepData()
        Thread.sleep(500)
        var lon = homeViewModel.long
        var lat = homeViewModel.latit
        Log.d("APITEST", "http://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey")
        val request = Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=$apiKey")
                .build()
        GlobalScope.launch(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Log.d("APITEST", "PASSED")
            } else {
                Log.d("APITEST", "FAILED")
            }
        }
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}