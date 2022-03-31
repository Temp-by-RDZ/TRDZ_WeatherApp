package com.trdz.weather.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MainViewModel(private val Data_Live: MutableLiveData<ApplicationStatus> = MutableLiveData()) : ViewModel() {

	fun getData(): LiveData<ApplicationStatus> {
		return Data_Live
	}

	fun getWeather() {
		Thread {
			Data_Live.postValue(ApplicationStatus.Load)
			for (i in 0..100) {
				sleep(100L); Data_Live.postValue(ApplicationStatus.Loading(i))
			}
			if ((Math.random() * 2).toInt() == 1) Data_Live.postValue(ApplicationStatus.Success(Any()))
			else Data_Live.postValue(ApplicationStatus.Error(IllegalAccessError()))
		}.start()
	}
}