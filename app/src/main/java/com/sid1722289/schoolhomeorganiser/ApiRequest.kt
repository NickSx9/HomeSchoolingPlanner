package com.sid1722289.schoolhomeorganiser

import com.sid1722289.schoolhomeorganiser.api.MealPlan
import com.sid1722289.schoolhomeorganiser.api.weatherResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("/b/605df61b7c9f775f638a09ad")
    fun getMealData(): Call<MealPlan>

    @GET
    fun getWeatherData(): Call<weatherResponse>
}