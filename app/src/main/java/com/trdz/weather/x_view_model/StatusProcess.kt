package com.trdz.weather.x_view_model

import com.trdz.weather.y_model.Weather

sealed class StatusProcess {
	object Load : StatusProcess()
	data class Loading(val progress: Int) : StatusProcess()
	data class Current(val dataCurrent: Weather) : StatusProcess()
	data class Success(val dataAll: List<Weather>) : StatusProcess()
	data class Error(val dataPast: Weather, val code: Int, val error: Throwable) : StatusProcess()
}