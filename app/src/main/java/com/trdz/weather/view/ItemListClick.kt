package com.trdz.weather.view

import com.trdz.weather.model.Weather

interface ItemListClick {
	fun onItemClick(weather: Weather)
}