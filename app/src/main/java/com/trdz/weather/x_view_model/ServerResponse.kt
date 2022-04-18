package com.trdz.weather.x_view_model

import com.trdz.weather.y_model.Weather
import com.trdz.weather.y_model.dto.AboutWeather

interface ServerResponse {
	fun newTarget(target: Int)
	fun putAll(data: List<AboutWeather>)
	fun putCurrent(data: Weather)
	fun error(error: Int)
}