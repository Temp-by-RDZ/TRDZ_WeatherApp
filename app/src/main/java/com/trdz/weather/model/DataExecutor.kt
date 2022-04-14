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
	}

	override fun getData(): List<Weather> {
		save()
		return getPossibilities()
	}

	private fun save() { //Времееное применение для теста!!!
		sharedPreference.edit().run {
			putInt("LAST_LOAD", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
			apply()
		}
	}

	fun needReload(): Boolean {
		return (sharedPreference.getInt("LAST_LOAD", 0) != Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
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
