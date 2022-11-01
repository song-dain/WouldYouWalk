package com.greedy.wouldyouwalk

import org.json.JSONException
import org.json.JSONObject

class WeatherData {

    lateinit var tempString: String
    lateinit var icon: String
    lateinit var weatherType: String
    private  var weatherId: Int = 0
    private var tempInt: Int = 0

    fun fromJson(jsonObject: JSONObject?): WeatherData? {
        try {
            var weatherData = WeatherData()
            weatherData.weatherId = jsonObject?.getJSONArray("weather")?.getJSONObject(0)?.getInt("id")!!
            weatherData.weatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main")
            weatherData.icon = updateWeatherIcon(weatherData.weatherId)
            val roundedTemp: Int = (jsonObject.getJSONObject("main").getDouble("temp")-273.15).toInt()
            weatherData.tempString = roundedTemp.toString()
            weatherData.tempInt = roundedTemp
            return weatherData
        } catch (e: JSONException){
            e.printStackTrace()
            return null
        }
    }

    private fun updateWeatherIcon(weatherId: Int): String {
        if(weatherId in 200..622) {
            return "rainy"
        } else if(weatherId in 701..781) {
            return "cloud"
        } else if(weatherId in 801..804) {
            return "cloud"
        } else {
            return "sun"
        }
    }

}