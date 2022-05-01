package com.trdz.weather.y_model

interface InternalData {
	fun load(lat:Double, lon:Double):ServerStatus
	fun save(weather: Weather)
	fun reloadCheckup(lat:Double, lon:Double): Boolean
}