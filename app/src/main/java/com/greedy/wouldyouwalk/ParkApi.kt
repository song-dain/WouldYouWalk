package com.greedy.wouldyouwalk

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ParkApi {

    /* Park 전체 목록 조회 */
    @GET("1/130")
    suspend fun parks(): Response<ParkRepository>


}