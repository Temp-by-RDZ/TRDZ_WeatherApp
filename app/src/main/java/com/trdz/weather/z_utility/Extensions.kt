package com.trdz.weather.z_utility

import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import com.trdz.weather.y_model.City
import com.trdz.weather.y_model.Weather
import com.trdz.weather.y_model.dto.AboutWeather
import com.trdz.weather.y_model.room.HistoryEntity

//region SnackBar

fun View.showSnackBar(message: Int, length: Int = Snackbar.LENGTH_LONG) {
	showSnackBar(resources.getString(message), length)
}

fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG) {
	Snackbar.make(this, message, length).show()
}

inline fun View.showSnackBar(message: Int, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	showSnackBar(resources.getString(message), length, action)
}

inline fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	Snackbar.make(this, message, length).apply {
		action()
		show()
	}
}

fun Snackbar.action(action: Int, color: Int? = null, listener: (View) -> Unit) {
	action(view.resources.getString(action), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
	setAction(action, listener)
	color?.let { setActionTextColor(color) }
}
//endregion

fun ImageView.loadSvg(url: String) {
	val imageLoader = ImageLoader.Builder(this.context)
		.componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
		.build()
	val request = ImageRequest.Builder(this.context)
		.crossfade(true)
		.crossfade(500)
		.data(url)
		.target(this)
		.build()
	imageLoader.enqueue(request)
}

fun toWeather(data: AboutWeather?): Weather {
	return if (data != null) (Weather(City("", data.info.lat, data.info.lon), data.fact.temp, data.fact.feels_like, data.fact.icon))
	else Weather()
}

fun toWeather(entityList: HistoryEntity): Weather {
	return with(entityList) {
		Weather(City(city, lat, lon), temperature, feelsLike, icon)
	}
}

fun toEntity(code: Long, weather: Weather): HistoryEntity {
	return with(weather) {
		HistoryEntity(0, code, city.name, city.lat, city.lon, temperature, sumare, icon)
	}
}