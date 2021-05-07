package com.sid1722289.schoolhomeorganiser.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sid1722289.schoolhomeorganiser.ApiRequest
import com.sid1722289.schoolhomeorganiser.R
import com.sid1722289.schoolhomeorganiser.database.LocationDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.internal.wait
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_WeatherAIP = "https://api.openweathermap.org/data/2.5/"

class HomeFragment : Fragment() {

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
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        val homeViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(HomeViewModel::class.java)
        var weatherImage: ImageView = root.findViewById(R.id.weatherImage)
        //Pulls the GPS data from the Database and sets it
        homeViewModel.prepData()
        Thread.sleep(500)
        //assigns the pulled data to lon/lat
        var lon = homeViewModel.long
        var lat = homeViewModel.latit
        val api = Retrofit.Builder()
                .baseUrl(BASE_WeatherAIP)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRequest::class.java)
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getWeatherData(lat, lon, apiKey).awaitResponse()
            Log.d("APITEST", response.isSuccessful.toString())
            if(response.isSuccessful) {
                var data = response.body()!!
                withContext(Dispatchers.Main) {
                    if (data.weather[0].main == "Clouds") {
                        weatherImage.setImageResource(R.drawable.cloudy)
                    }
                    if (data.weather[0].main == "Clear") {
                        weatherImage.setImageResource(R.drawable.sunny)
                    }
                    if (data.weather[0].main == "Rain") {
                        weatherImage.setImageResource(R.drawable.rainy)
                    }
                    Toast.makeText(activity as Context, "You location is " + data.name, Toast.LENGTH_SHORT).show()
                    Log.d("WEATHER", data.weather[0].main)
                }
            }
        }
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}