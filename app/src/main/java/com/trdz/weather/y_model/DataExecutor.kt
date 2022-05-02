package com.trdz.weather.y_model

import android.util.Log
import com.trdz.weather.x_view_model.ServerResponse
import com.trdz.weather.z_utility.ERROR_NUMBER
import java.util.*

class DataExecutor : Repository {

	private val externalSource1: ExternalSource = ServerReceiver()
	private val externalSource2: ExternalSource = ServerRetrofit()
	private val internalData: InternalData = ServerRoom()

	override fun getEurope() = listEurope()
	override fun getAsia() = listAsia()
	override fun getAfrica() = listAfrica()
	override fun getOther() = listOther()

	fun connection(serverListener: ServerResponse, weather: Weather) {
		Log.d("@@@", "Rep - start connection")
		Thread {
			var lat = weather.city.lat
			var lon = weather.city.lon
			if (internalData.reloadCheckup(lat,lon)) {
				Log.d("@@@", "Rep - data found")
				serverListener.put(getLast(weather))
				return@Thread
			}
			if (weather.city.lat != ERROR_NUMBER) {
				lat = Math.max(-89.90, Math.min(weather.city.lat, 89.90))
				lon = Math.max(-179.90, Math.min(weather.city.lon, 179.90))}
			Log.d("@@@", "Rep - download")
			val serverStatus = externalSource2.load(lat,lon)
			serverListener.newTarget(100)
			Log.d("@@@", "Rep - get result with code:"+serverStatus.code.toString())
			if (serverStatus.result != null) {
				val result = makeCurrent(weather,serverStatus.result)
				internalData.save(result)
				serverListener.put(result)
			}
			else serverListener.error(serverStatus.code,getLast(weather))
		}.start()
	}

	private fun getLast(weather: Weather): Weather {
		val result = internalData.load(weather.city.lat,weather.city.lon)
		return if (result.result!=null) makeCurrent(weather,result.result)
		else Weather(City("Ошибка подключениея",000.0,000.0),666,999)
	}

	private fun makeCurrent(weather: Weather, data: Weather): Weather {
		return weather.copy(city = City(weather.city.name,data.city.lat,data.city.lon), temperature = data.temperature, sumare = data.sumare, icon = data.icon)
	}
}
