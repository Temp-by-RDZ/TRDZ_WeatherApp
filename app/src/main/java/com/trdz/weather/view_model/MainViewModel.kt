package com.trdz.weather.view_model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trdz.weather.model.DataExecutor
import com.trdz.weather.model.Weather
import java.lang.Thread.sleep

class MainViewModel(
	private val Data_Live: MutableLiveData<ApplicationStatus> = MutableLiveData(),
	private val repository: DataExecutor = DataExecutor(),
) : ViewModel() {

	private var stage: Int = 0

	fun getData(): LiveData<ApplicationStatus> {
		return Data_Live
	}

	fun getWeather() {
		Thread {
			Data_Live.postValue(ApplicationStatus.Load)
			repository.connection()
			sleep(30L)
			var status: Int
			do {status = repository.status()
				Data_Live.postValue(ApplicationStatus.Loading(status))
			} while (status > -1 && status < 100)
			if (status == 100) Data_Live.postValue(ApplicationStatus.Success(repository.getData()))
			else Data_Live.postValue(ApplicationStatus.Error(repository.getTemporalData(), IllegalAccessError()))
		}.start()
	}

	fun getSpecifiqWeather() {
		Thread {
			Data_Live.postValue(ApplicationStatus.Load)
			repository.connectionFast()
			sleep(30L)
			var status: Int
			do {status = repository.status()
				Data_Live.postValue(ApplicationStatus.Loading(status))
			} while (status > -1 && status < 100)
			if (status == 100) Data_Live.postValue(ApplicationStatus.Success(repository.getData()))
			else Data_Live.postValue(ApplicationStatus.Error(repository.getTemporalData(), IllegalAccessError()))
		}.start()
	}

	fun initialize(sharedPreferences: SharedPreferences) {//Времееное применение для теста!!!
		repository.link(sharedPreferences)
	}
}