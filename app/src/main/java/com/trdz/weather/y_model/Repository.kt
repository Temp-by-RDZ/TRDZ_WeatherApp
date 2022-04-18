package com.trdz.weather.y_model

interface Repository {
	fun getTemporalData():List<Weather>
	fun getData():List<Weather>
}