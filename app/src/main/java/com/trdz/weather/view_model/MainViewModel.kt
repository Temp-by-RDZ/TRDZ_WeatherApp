package com.trdz.weather.view_model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trdz.weather.model.DataExecutor
import java.lang.Thread.sleep

class MainViewModel(
	private val dataLive: MutableLiveData<ApplicationStatus> = MutableLiveData(),
	private val repository: DataExecutor = DataExecutor(),
) : ViewModel() {

	fun getData(): LiveData<ApplicationStatus> {
		return dataLive
	}

	fun getWeather() {
		if (repository.needReload()) {
			Thread {
				dataLive.postValue(ApplicationStatus.Load)
				repository.connection()
				sleep(30L)
				var status: Int
				do {
					status = repository.status()
					dataLive.postValue(ApplicationStatus.Loading(status))
				} while (status > -1 && status < 100)
				if (status == 100) dataLive.postValue(ApplicationStatus.Success(repository.getData()))
				else dataLive.postValue(ApplicationStatus.Error(repository.getTemporalData(), IllegalAccessError()))
			}.start()
		} else dataLive.postValue(ApplicationStatus.Success(repository.getTemporalData()))
	}

	fun getSpecifiqWeather() {
		Thread {
			dataLive.postValue(ApplicationStatus.Load)
			repository.connectionFast()
			sleep(30L)
			var status: Int
			do {
				status = repository.status()
				dataLive.postValue(ApplicationStatus.Loading(status))
			} while (status > -1 && status < 100)
			if (status == 100) dataLive.postValue(ApplicationStatus.Success(repository.getData()))
			else dataLive.postValue(ApplicationStatus.Error(repository.getTemporalData(), IllegalAccessError()))
		}.start()
	}

	fun initialize(sharedPreferences: SharedPreferences) {//Времееное применение для теста!!!
		repository.link(sharedPreferences)
	}
}