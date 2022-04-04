package com.trdz.weather.model

interface Repository {
	fun getTemporalData():List<Weather>
	fun getData():List<Weather>
}