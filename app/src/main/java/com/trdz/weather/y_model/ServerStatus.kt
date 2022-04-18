package com.trdz.weather.y_model

import com.trdz.weather.y_model.dto.AboutWeather

data class ServerStatus(val code: Int, val result: AboutWeather? = null)
