package com.trdz.weather.w_view.windows

import com.trdz.weather.y_model.Weather

interface ItemListClick {
	fun onItemClick(weather: Weather)
}