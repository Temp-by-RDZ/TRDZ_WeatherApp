package com.trdz.weather.view_model

import com.trdz.weather.model.Weather

sealed class ApplicationStatus {
	object Load:ApplicationStatus()
	data class Loading(val progress: Int):ApplicationStatus()
	data class Success(val dataCurrent:Weather):ApplicationStatus()
	data class Error(val dataPast:Weather, val error: Throwable):ApplicationStatus()
}