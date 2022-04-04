package com.trdz.weather.model

import android.content.SharedPreferences
import java.lang.Thread.sleep

class DataExecutor : Repository {

	private var process = 0

	private var _sharedPreference: SharedPreferences? = null
	private val sharedPreference get() = _sharedPreference!!

	fun link(sharedPreferences: SharedPreferences) { //Времееное применение для теста!!!
		_sharedPreference = sharedPreferences
	}

	override fun getTemporalData(): Weather {
		return load()
	}

	private fun load(): Weather { //Времееное применение для теста!!!
		return Weather(
			City(
				sharedPreference.getString("LAST_NAME", "Ошибка") ?: "Ошибка",
				sharedPreference.getFloat("LAST_LAT", 0.0f),
				sharedPreference.getFloat("LAST_LON", 0.0f)
			),
			sharedPreference.getInt("LAST_TRL", 0),
			sharedPreference.getInt("LAST_TFL", 0)
		)
	}

	override fun getData(): Weather {
		val data: Weather = getPossibilities().random()
		save(data)
		return data
	}

	private fun save(data: Weather) { //Времееное применение для теста!!!
		val myEditor = sharedPreference.edit()
		myEditor.putString("LAST_NAME", data.city.name)
		myEditor.putFloat("LAST_LAT", data.city.lat)
		myEditor.putFloat("LAST_LON", data.city.lon)
		myEditor.putInt("LAST_TRL", data.temperature)
		myEditor.putInt("LAST_TFL", data.sumare)
		myEditor.apply()
	}

	fun connection() {
		process = 0
		Thread {
			while (process < 100) {
				sleep(66L);
				if ((Math.random() * 100).toInt() < 99) process++
				else {
					process = -1
					break
				}
			}
		}.start()
	}

	fun status(): Int {
		return process
	}


}