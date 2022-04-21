package com.trdz.weather.x_view_model

import com.trdz.weather.y_model.Weather

sealed class StatusProcess {
	object Load : StatusProcess()
	data class Loading(val progress: Int) : StatusProcess()
	data class Success(val dataCurrent: Weather) : StatusProcess()
	data class TransferList(val dataAll: List<Weather>) : StatusProcess()
	data class Error(val dataProblematic: Weather, val dataPast: Weather, val code: Int, val error: Throwable) : StatusProcess()
}