package com.trdz.weather.x_view_model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trdz.weather.y_model.DataExecutor
import com.trdz.weather.y_model.Weather
import com.trdz.weather.y_model.dto.AboutWeather
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

	fun getWeather() {
		if (repository.needReload()) {
			Thread {
				dataLive.postValue(StatusProcess.Load)
				repository.connection()
				sleep(30L)
				var status: Int
				do {
					status = repository.status()
					dataLive.postValue(StatusProcess.Loading(status))
				} while (status > -1 && status < 100)
				if (status == 100) dataLive.postValue(StatusProcess.Success(repository.getData()))
				else dataLive.postValue(StatusProcess.Error(Weather(),404, IllegalAccessError()))
			}.start()
		} else dataLive.postValue(StatusProcess.Success(repository.getTemporalData()))
	}

	fun getSpecificWeather() {
		quest = 1
		status = 0
		Thread {
			dataLive.postValue(StatusProcess.Load)
			quest +=79
			repository.connectionFast(this@MainViewModel)
			do {
				sleep(5L)
				if (status<(quest-1)) status++
				dataLive.postValue(StatusProcess.Loading(status))
			} while (status > -1 && status < 99)
		}.start()
	}

	fun initialize(sharedPreferences: SharedPreferences) {//Времееное применение для теста!!!
		repository.link(sharedPreferences)
	}

	override fun newTarget(target: Int) {
		quest += target
	}

	override fun putAll(data: List<AboutWeather>) {
		status = 100
		dataLive.postValue(StatusProcess.Success(repository.getData()))
	}

	override fun putCurrent(data: Weather) {
		status = 100
		dataLive.postValue(StatusProcess.Success(listOf(data)))
	}

	override fun error(error: Int) {
		status = -100
		dataLive.postValue(StatusProcess.Error(Weather(), error, IllegalAccessError()))
	}
}