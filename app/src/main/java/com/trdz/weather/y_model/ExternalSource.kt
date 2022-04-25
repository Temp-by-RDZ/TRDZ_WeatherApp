package com.trdz.weather.y_model

interface ExternalSource {
	fun load(lat:Double, lon:Double):ServerStatus
}