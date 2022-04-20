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

	override fun getEurope() = listEurope()
	override fun getAsia() = listAsia()
	override fun getAfrica() = listAfrica()
	override fun getOther() = listOther()

	fun needReload(): Boolean {
		return (sharedPreference.getInt("LAST_LOAD", 0) != Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
	}

	fun connection(serverListener: ServerResponse, weather: Weather) {
		process = 0
		Thread {
			val serverStatus = ServerReceiver().load(weather.city.lat,weather.city.lon)
			serverListener.newTarget(29)
			sleep(10L)
			if (serverStatus.result != null)	serverListener.put(makeCurrent(weather,serverStatus.result))
			else serverListener.error(serverStatus.code)
		}.start()
	}

	private fun makeCurrent(weather: Weather, data: AboutWeather): Weather {
		return weather.copy(city = City(weather.city.name,data.info.lat,data.info.lon), temperature = data.fact.temp, sumare = data.fact.feels_like)
	}

}
