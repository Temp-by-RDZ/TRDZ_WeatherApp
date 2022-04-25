package com.trdz.weather.x_view_model

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trdz.weather.y_model.*
import kotlinx.android.synthetic.main.fragment_window_list.view.*

class MainViewModel(
	private val dataLive: MutableLiveData<StatusProcess> = MutableLiveData(),
	private val repository: DataExecutor = DataExecutor(),
) : ViewModel(), ServerResponse {

	fun getData(): LiveData<StatusProcess> {
		return dataLive
	}

	fun initialize(sharedPreferences: SharedPreferences) {//Времееное применение для теста!!!
		repository.link(sharedPreferences)
	}

	fun getWeather(weather: Weather = Weather(currentCity())) {
		dataLive.postValue(StatusProcess.Load)
		repository.connection(this@MainViewModel, weather)
		// Пришлось заменить подход ради лучшей синхронизации
		// так как оказалось что паралельные обращения в обсервере перезаписываются с опасной частотой
	}

	fun repeat(weather: Weather = Weather(currentCity())) {
		repository.connection(this@MainViewModel, weather)
	}

	fun getWeatherList(id: Int) {
		Log.d("@@@", "Mod - list request")
		when (id) {
			1 -> dataLive.postValue(StatusProcess.TransferList(repository.getAsia()))
			2 -> dataLive.postValue(StatusProcess.TransferList(repository.getAfrica()))
			3 -> dataLive.postValue(StatusProcess.TransferList(repository.getEurope()))
			4 -> dataLive.postValue(StatusProcess.TransferList(repository.getOther()))
		}
	}

	override fun newTarget(target: Int) {
		dataLive.postValue(StatusProcess.Loading(target))
	}

	override fun put(data: Weather) {
		Log.d("@@@", "Mod - get success answer")
		dataLive.postValue(StatusProcess.Success(data))
	}

	override fun error(error: Int, weatherBad: Weather) {
		Log.d("@@@", "Mod - get bad answer")
		dataLive.postValue(StatusProcess.Error(weatherBad, Weather(), error, IllegalAccessError()))
	}
}