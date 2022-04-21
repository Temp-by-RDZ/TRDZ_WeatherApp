package com.trdz.weather.x_view_model

import com.trdz.weather.y_model.Weather

interface ServerResponse {
	fun newTarget(target: Int)
	fun put(data: Weather)
	fun error(error: Int, weatherBad: Weather)
}