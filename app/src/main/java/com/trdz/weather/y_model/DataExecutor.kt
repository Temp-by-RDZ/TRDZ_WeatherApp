package com.trdz.weather.y_model

import android.content.SharedPreferences
import com.trdz.weather.x_view_model.ServerResponse
import com.trdz.weather.y_model.dto.AboutWeather
import java.lang.Thread.sleep
import java.util.*

class DataExecutor : Repository {

	private var process = 0

	private var _sharedPreference: SharedPreferences? = null
	private val sharedPreference get() = _sharedPreference!!

	fun link(sharedPreferences: SharedPreferences) {
		_sharedPreference = sharedPreferences
	}

	private fun save() {
		sharedPreference.edit().run {
			putInt("LAST_LOAD", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
			apply()
		}
	}

	override fun getTemporalData(): List<Weather> {
		return getData()
	}

	override fun getData(): List<Weather> {
		save()
		return getPossibilities()
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

	fun connectionFast(serverListener: ServerResponse, lan:Double = -666.0, lon:Double = -666.0) {
		process = 0
		Thread {
			val serverStatus = ServerReceiver().load(lan,lon)
			serverListener.newTarget(29)
			sleep(10L)
			if (serverStatus.result!=null)	serverListener.putCurrent(makeCurrent(serverStatus.result))
			else serverListener.error(serverStatus.code)
		}.start()
	}

	private fun makeCurrent(data: AboutWeather): Weather {
		return Weather(City("",data.info.lat.toFloat(),data.info.lon.toFloat()),data.fact.temp,data.fact.feels_like)
	}

	fun status() = process
}
