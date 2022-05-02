package com.trdz.weather.y_model

import android.util.Log
import com.google.gson.GsonBuilder
import com.trdz.weather.BuildConfig
import com.trdz.weather.z_utility.DOMAIN
import com.trdz.weather.z_utility.ERROR_NUMBER
import com.trdz.weather.z_utility.toWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerRetrofit:ExternalSource {


	override fun load(lat: Double, lon: Double): ServerStatus {
		if (lat== ERROR_NUMBER) return dynamicLoad()
		val weatherAPI = Retrofit.Builder().apply {
			baseUrl(DOMAIN)
			addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
		}.build().create(ServerRetrofitApi::class.java)

		return try {
			val response = weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY,lat,lon).execute()
			if (response.isSuccessful) ServerStatus(response.code(),toWeather(response.body()))
			else ServerStatus(response.code())
		}
		catch (Ignored: Exception) {
			Log.d("@@@", "Ser- Connection Error")
			ServerStatus(-1)
		}
		}

	private fun dynamicLoad(): ServerStatus {
		val weatherAPI = Retrofit.Builder().apply {
			baseUrl(DOMAIN)
			addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
		}.build().create(ServerRetrofitDynamicApi::class.java)

		val response = weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY).execute()
		return if (response.isSuccessful) ServerStatus(response.code(), toWeather(response.body()))
		else ServerStatus(response.code())
	}
}
