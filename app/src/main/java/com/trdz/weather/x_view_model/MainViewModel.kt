package com.trdz.weather.x_view_model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trdz.weather.y_model.*
import kotlinx.android.synthetic.main.fragment_window_list.view.*
import java.lang.Thread.sleep

class MainViewModel(
	private val dataLive: MutableLiveData<StatusProcess> = MutableLiveData(),
	private val repository: DataExecutor = DataExecutor(),
) : ViewModel(), ServerResponse {

	var status: Int = 0
	var quest: Int = 0

	fun getData(): LiveData<StatusProcess> {
		return dataLive
	}

	fun initialize(sharedPreferences: SharedPreferences) {//Времееное применение для теста!!!
		repository.link(sharedPreferences)
	}

	fun getWeather(weather: Weather = Weather(currentCity())) {
		status = 0
		Thread {
			dataLive.postValue(StatusProcess.Load)
			quest = 90
			repository.connection(this@MainViewModel, weather)
			do {
				sleep(5L)
				if (status < (quest - 1)) status++
				dataLive.postValue(StatusProcess.Loading(status))
			} while (status > -1 && status < 99)
		}.start()
	}

	fun getWeatherList(id: Int) {
		when (id) {
			1 -> dataLive.postValue(StatusProcess.TransferList(repository.getAsia()))
			2 -> dataLive.postValue(StatusProcess.TransferList(repository.getAfrica()))
			3 -> dataLive.postValue(StatusProcess.TransferList(repository.getEurope()))
			4 -> dataLive.postValue(StatusProcess.TransferList(repository.getOther()))
		}
	}

	override fun newTarget(target: Int) {
		quest = target
	}

	override fun put(data: Weather) {
		status = 100
		dataLive.postValue(StatusProcess.Success(data))
	}

	override fun error(error: Int,weatherBad: Weather) {
		status = -100
		dataLive.postValue(StatusProcess.Error(weatherBad, Weather(), error, IllegalAccessError()))
	}
}