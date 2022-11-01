package com.greedy.wouldyouwalk

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ParkInfoService {

    fun getParkInfoService() : ParkApi {

        return Retrofit.Builder()
            .baseUrl("http://openapi.seoul.go.kr:8088/75624e6c526c65653432764a667144/json/SearchParkInfoService/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ParkApi::class.java)

    }
}