package com.trdz.weather.w_view.fragments_windows

import com.trdz.weather.y_model.Weather

interface ItemListClick {
	fun onItemClick(weather: Weather)
}