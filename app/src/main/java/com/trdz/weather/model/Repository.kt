package com.trdz.weather.model

interface Repository {
	fun getTemporalData():Weather
	fun getData():Weather
}