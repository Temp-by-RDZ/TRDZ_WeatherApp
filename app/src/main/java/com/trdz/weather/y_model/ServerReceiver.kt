package com.trdz.weather.y_model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.trdz.weather.BuildConfig
import com.trdz.weather.y_model.dto.AboutWeather
import com.trdz.weather.z_utility.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ServerReceiver() {

	fun load(plat: Double, plon: Double): ServerStatus {
		var responseCode = 0

		val uri = URL(StringBuilder("").apply {
			append(DOMAIN)
			append(PACKAGE)
			append(PARAM1)
			if (plon != 666.0) {
			val lat = Math.max(-90.0,Math.min(plat, 90.0))
			val lon = Math.max(-180.0,Math.min(plon, 180.0))
			append("=")
			append(lat)
			append("&")
			append(PARAM2)
			append("=")
			append(lon) } }.toString())

		val urlConnection: HttpsURLConnection = (uri.openConnection() as HttpsURLConnection).apply {
			connectTimeout = 1000
			readTimeout = 1000
			addRequestProperty(LOGIN,BuildConfig.WEATHER_API_KEY)
		}
		Thread.sleep(500);


		try {
			responseCode = urlConnection.responseCode
			if (responseCode < 300) {
				val headers = urlConnection.headerFields
				val responseMessage = urlConnection.responseMessage

				val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
				val aboutWeather: AboutWeather = Gson().fromJson(buffer, AboutWeather::class.java)
				return ServerStatus(responseCode, aboutWeather)
			}
		}
		catch (Ignored: JsonSyntaxException) { return ServerStatus(responseCode) }
		finally { urlConnection.disconnect() }
		return ServerStatus(responseCode)

	}
}