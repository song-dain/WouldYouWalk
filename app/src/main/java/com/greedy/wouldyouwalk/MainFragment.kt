package com.greedy.wouldyouwalk

import android.Manifest
import android.app.DownloadManager.Request
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.gson.JsonObject
import com.greedy.wouldyouwalk.databinding.FragmentMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"





class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    /*--날씨 관련--*/
    private var binding: FragmentMainBinding?= null
    private lateinit var weatherState: TextView
    private lateinit var temperature: TextView
    private lateinit var weatherTip: TextView
    private lateinit var weatherIcon: ImageView

    private lateinit var mLocationManager: LocationManager
    private lateinit var mLocationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentMainBinding.inflate(inflater, container, false)

        binding = fragmentBinding
        return fragmentBinding.root
    }

    /*날씨 관련 */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding?.mainFragment = this
        binding?.apply {
            temperature = temperatureTv
            weatherState = weatherTv
            weatherTip = weatherTipTv
            weatherIcon = weatherIc
        }

    }

    override fun onResume() {
        super.onResume()
        getWeatherInCurrentLocation()
    }

    private fun getWeatherInCurrentLocation() {
        mLocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mLocationListener = LocationListener { p0 ->
            val params: RequestParams = RequestParams()
            params.put("lat", p0.latitude)
            params.put("lon", p0.longitude)
            params.put("appid", Companion.API_KEY)
            doNetWorking(params)
        }

        if(ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), WEATHER_REQUEST)
            return
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener)
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener)
    }

    private fun doNetWorking(params: RequestParams) {
        var client = AsyncHttpClient()
        client.get(WEATHER_URL, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONObject?
            ) {
                val weatherData = WeatherData().fromJson(response)
                if(weatherData != null) {
                    updateWeather(weatherData)
                }
            }
        })
    }

    private fun updateWeather(weather: WeatherData) {
        temperature.setText(weather.tempString+"°C")
        weatherState.setText(weather.weatherType)
        val resourceID = resources.getIdentifier(weather.icon, "drawable", activity?.packageName)
        weatherIcon.setImageResource(resourceID)
        weatherTip.setText(updateWeatherTip(weather.weatherType))
    }

    private fun updateWeatherTip(weatherType: String): String {

        var setText = ""

        if(weatherType == "Clear"){
            setText = "산책 가기 좋은 날씨네요!"
        } else if(weatherType == "rain") {
            setText = "우산 꼭 챙기세요!"
        } else if(weatherType == "snow") {
            setText = "눈길에 미끄러지지 않게 조심하세요!"
        } else if(weatherType == "Clouds") {
            setText = "햇빛이 적당한 날씨입니다!"
        }

        return setText
    }

    override fun onPause() {
        super.onPause()
        if(mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener)
        }
    }



    companion object {
        /*-- 날씨 관련 --*/
        const val API_KEY: String = "2c3f8ecdb995c5057ad060cf434d30e8"
        const val WEATHER_URL: String = "https://api.openweathermap.org/data/2.5/weather"
        const val MIN_TIME: Long = 5000
        const val MIN_DISTANCE: Float = 1000F
        const val WEATHER_REQUEST: Int = 102

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment main.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}