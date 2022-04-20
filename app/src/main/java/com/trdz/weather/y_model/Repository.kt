package com.trdz.weather.y_model

interface Repository {
	fun getEurope():List<Weather>
	fun getAsia():List<Weather>
	fun getAfrica():List<Weather>
	fun getOther():List<Weather>
}