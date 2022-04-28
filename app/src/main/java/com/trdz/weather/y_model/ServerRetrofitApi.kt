package com.trdz.weather.y_model

import com.trdz.weather.y_model.dto.AboutWeather
import com.trdz.weather.z_utility.API_KEY
import com.trdz.weather.z_utility.PACKAGE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ServerRetrofitApi {
	@GET(PACKAGE)
	fun getWeather(
		@Header(API_KEY) apikey: String,
		@Query("lat") lat: Double,
		@Query("lon") lon: Double
	): Call<AboutWeather>
}
interface ServerRetrofitDynamicApi {
	@GET(PACKAGE)
	fun getWeather(
		@Header(API_KEY) apikey: String
	): Call<AboutWeather>
}