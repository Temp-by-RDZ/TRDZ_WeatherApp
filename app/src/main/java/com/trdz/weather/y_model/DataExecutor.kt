package com.trdz.weather.y_model

import android.content.SharedPreferences
import android.util.Log
import com.trdz.weather.x_view_model.ServerResponse
import com.trdz.weather.y_model.dto.AboutWeather
import com.trdz.weather.z_utility.ERROR_NUMBER
import java.util.*

class DataExecutor : Repository {

	private var _sharedPreference: SharedPreferences? = null
	private val sharedPreference get() = _sharedPreference!!
	private val externalSource1: ExternalSource = ServerReceiver()
	private val externalSource2: ExternalSource = ServerRetrofit()

	fun link(sharedPreferences: SharedPreferences) {
		_sharedPreference = sharedPreferences
	}

	override fun getEurope() = listEurope()
	override fun getAsia() = listAsia()
	override fun getAfrica() = listAfrica()
	override fun getOther() = listOther()

	private fun save() {	//Будущее сохранение успешного обращения
		sharedPreference.edit().run {
			putInt("LAST_LOAD", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
			apply()
		}
	}

	fun needReload(): Boolean { //Будущее обращение к наличию сохраненного обращения
		return (sharedPreference.getInt("LAST_LOAD", 0) != Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
	}

	fun connection(serverListener: ServerResponse, weather: Weather) {
		Log.d("@@@", "Rep - start connection")
		Thread {
			var lat = weather.city.lat
			var lon = weather.city.lon
			if (weather.city.lat != ERROR_NUMBER) {
				lat = Math.max(-89.90, Math.min(weather.city.lat, 89.90))
				lon = Math.max(-179.90, Math.min(weather.city.lon, 179.90))}
			val serverStatus = externalSource2.load(lat,lon)
			serverListener.newTarget(100)
			// Здесь будет обработка с сохранением примерных координат и времени запроса
			Log.d("@@@", "Rep - get result with code:"+serverStatus.code.toString())
			if (serverStatus.result != null) serverListener.put(makeCurrent(weather,serverStatus.result))
			else serverListener.error(serverStatus.code,weather)
		}.start()
	}

	private fun makeCurrent(weather: Weather, data: AboutWeather): Weather {
		return weather.copy(city = City(weather.city.name,data.info.lat,data.info.lon), temperature = data.fact.temp, sumare = data.fact.feels_like)
	}

}
