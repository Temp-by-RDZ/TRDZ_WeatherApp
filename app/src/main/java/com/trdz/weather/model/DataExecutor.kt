package com.trdz.weather.model

import android.content.SharedPreferences
import java.lang.Thread.sleep
import java.util.*

class DataExecutor : Repository {

	private var process = 0

	private var _sharedPreference: SharedPreferences? = null
	private val sharedPreference get() = _sharedPreference!!

	fun link(sharedPreferences: SharedPreferences) { //Времееное применение для теста!!!
		_sharedPreference = sharedPreferences
	}

	override fun getTemporalData(): List<Weather> {
		return getData()
		//return load()
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

	override fun getData(): List<Weather> {
		save()
		return getPossibilities()
	}

	private fun save() { //Времееное применение для теста!!!
		sharedPreference.edit().run {
			putInt("LAST_LOAD", Calendar.DAY_OF_MONTH)
			apply()
		}
	}

	fun needReload(): Boolean {
		return (sharedPreference.getInt("LAST_LOAD", 0) != Calendar.DAY_OF_MONTH)
	}

	fun connection() {
		process = 0
		Thread {
			while (process < 100) {
				sleep(66L);
				if ((Math.random() * 100).toInt() < 99) process++
				else {
					process = -1; break
				}
			}
		}.start()
	}

	fun connectionFast() {
		process = 0
		Thread {
			while (process < 100) {
				sleep(11L);
				if ((Math.random() * 100).toInt() < 99) process++
				else {
					process = -1;break
				}
			}
		}.start()
	}

	fun status() = process
}
