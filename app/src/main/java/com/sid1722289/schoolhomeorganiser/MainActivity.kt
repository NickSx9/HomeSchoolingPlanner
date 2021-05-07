package com.sid1722289.schoolhomeorganiser

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.of
import androidx.lifecycle.ViewModelStores.of
import com.google.android.gms.location.*
import com.sid1722289.schoolhomeorganiser.database.GPSLocation
import okhttp3.Headers.Companion.of

import okhttp3.OkHttpClient

import java.util.*


class MainActivity : AppCompatActivity(), AppNavigator {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var PERMISSION_ID = 11
    lateinit var lm: LocationManager
    lateinit var viewModel: MainActivityViewModel
    var longitude: String = ""
    var latitude: String = ""
    private var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)

        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var loc: Location? = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        var ll = object:LocationListener{
            override fun onLocationChanged(p0: Location) {
                reverseGeocode(p0)
            }
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3000.2f, ll)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_lesson, R.id.nav_mealPlanner, R.id.nav_schedule, R.id.nav_links, R.id.nav_settings, R.id.nav_addLesson), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    private fun reverseGeocode(loc: Location?) {
        var gc = Geocoder(this, Locale.UK)
        var addresses =gc.getFromLocation(loc!!.latitude, loc.longitude, 2)
        var address = addresses.get(0)
        longitude = address.longitude.toString()
        latitude = address.latitude.toString()
        var gpsLocation = GPSLocation(Latitude = address.latitude.toString(), Longitude = address.longitude.toString())
        viewModel.insertGpsData(gpsLocation)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun navigateToLessonSettings() {
    }
}