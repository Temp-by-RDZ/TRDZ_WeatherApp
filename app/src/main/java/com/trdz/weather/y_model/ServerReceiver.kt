package com.trdz.weather.y_model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.trdz.weather.BuildConfig
import com.trdz.weather.y_model.dto.AboutWeather
import com.trdz.weather.z_utility.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ServerReceiver():ExternalSource {

	override fun load(lat: Double, lon: Double): ServerStatus {
		var responseCode = -1

		val uri = URL(StringBuilder("").apply {
			append(DOMAIN)
			append(PACKAGE)
			if (lon != ERROR_NUMBER) {
				append(PARAM1)
				append("=")
				append(lat)
				append("&")
				append(PARAM2)
				append("=")
				append(lon)
			}
		}.toString())

		val urlConnection: HttpsURLConnection = (uri.openConnection() as HttpsURLConnection).apply {
			connectTimeout = 1000
			readTimeout = 1000
			addRequestProperty(API_KEY, BuildConfig.WEATHER_API_KEY)
		}

		try { responseCode = urlConnection.responseCode }
		catch (Ignored: Exception) {
			Log.d("@@@", "Ser- Connection Error")
			return ServerStatus(responseCode) }

		try {
			if (responseCode in 1..299) {

				val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
				val aboutWeather: AboutWeather = Gson().fromJson(buffer, AboutWeather::class.java)

				return ServerStatus(responseCode, toWeather(aboutWeather))
			}
		}
		catch (Ignored: JsonSyntaxException) {
			return ServerStatus(responseCode)
		}
		finally {
			urlConnection.disconnect()
		}
		return ServerStatus(responseCode)

	}
}