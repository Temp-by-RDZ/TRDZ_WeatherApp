package com.trdz.weather.y_model

import com.trdz.weather.MyApp
import com.trdz.weather.z_utility.toWeather
import java.util.*

class ServerRoom:ExternalSource {

	override fun load(lat: Double, lon: Double): ServerStatus {
		val result = MyApp.getHistoryDao().getHistoryForCity((Calendar.getInstance().get(Calendar.YEAR)*1000+Calendar.getInstance().get(Calendar.DAY_OF_YEAR)).toLong())
		if (result.isNotEmpty()) {
			result.forEach {
				if (lat==it.lat) {
					if (lon==it.lon) {
						return ServerStatus(200,toWeather(it))
					}
				}
			}
		}
		return ServerStatus(404,Weather())

	}
}