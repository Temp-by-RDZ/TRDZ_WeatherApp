package com.trdz.weather.y_model

import android.util.Log
import com.trdz.weather.MyApp
import com.trdz.weather.y_model.room.HistoryEntity
import com.trdz.weather.z_utility.toEntity
import com.trdz.weather.z_utility.toWeather
import java.util.*

class ServerRoom:InternalData {

	private fun getData():List<HistoryEntity> {
		return MyApp.getHistoryDao().getHistoryForCity(getCode())
	}
	private fun getCode(): Long {
		return (Calendar.getInstance().get(Calendar.YEAR)*1000+Calendar.getInstance().get(Calendar.DAY_OF_YEAR)).toLong()
	}

	override fun reloadCheckup(lat: Double, lon: Double):Boolean {
		val result = getData()
		return find(result,lat,lon)!=-1
	}

	override fun save(weather: Weather) {
		Log.d("@@@", "!Added Gen: "+getCode())
		MyApp.getHistoryDao().insert(toEntity(getCode(),weather))
	}

	override fun load(lat: Double, lon: Double): ServerStatus {
		val result = getData()
		val position = find(result,lat,lon)
		return if (position!=-1) ServerStatus(200,toWeather(result[position]))
		else ServerStatus(404)
	}

	private fun find(result: List<HistoryEntity>, lat: Double, lon: Double): Int {
		if (result.isNotEmpty()) {
			result.forEachIndexed { index, it ->
				if (lat.toInt()==it.lat.toInt()) {
					if (lon.toInt()==it.lon.toInt()) {
						return index
					}
				}
			}
		}
		return -1
	}
}