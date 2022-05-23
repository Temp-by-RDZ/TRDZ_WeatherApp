package com.trdz.weather.y_model

import android.util.Log
import com.trdz.weather.BuildConfig
import com.trdz.weather.MyApp
import com.trdz.weather.z_utility.ERROR_NUMBER
import com.trdz.weather.z_utility.toWeather

class ServerRetrofit: ExternalSource {

	override fun load(lat: Double, lon: Double): ServerStatus {
		if (lat == ERROR_NUMBER) return dynamicLoad()

		val weatherAPI = MyApp.getRetrofitCustom()

		return try {
			val response = weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).execute()
			if (response.isSuccessful) ServerStatus(response.code(), toWeather(response.body()))
			else ServerStatus(response.code())
		}
		catch (Ignored: Exception) {
			Log.d("@@@", "Ser- Connection Error")
			ServerStatus(-1)
		}
	}

	private fun dynamicLoad(): ServerStatus {
		val weatherAPI = MyApp.getRetrofitDynamic()

		val response = weatherAPI.getWeather(BuildConfig.WEATHER_API_KEY).execute()
		return if (response.isSuccessful) ServerStatus(response.code(), toWeather(response.body()))
		else ServerStatus(response.code())
	}
}
