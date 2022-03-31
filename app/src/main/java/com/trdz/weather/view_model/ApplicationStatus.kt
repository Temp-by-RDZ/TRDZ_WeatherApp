package com.trdz.weather.view_model


sealed class ApplicationStatus {
	object Load:ApplicationStatus()
	data class Loading(val progress: Int):ApplicationStatus()
	data class Success(val data:Any):ApplicationStatus()
	data class Error(val error: Throwable):ApplicationStatus()
}