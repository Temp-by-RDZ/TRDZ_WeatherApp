package com.trdz.weather.model

import java.lang.Thread.sleep

class DataExecutor : Repository {

	private var process = 0

	override fun getTemporalData(): Weather {
		return Weather()
	}

	override fun getData(): Weather {
		return Weather()
	}

	fun connection() {
		process = 0
		Thread {
			while (process < 100) {
				sleep(66L);
				if ((Math.random() * 100).toInt() < 98) process++
				else { process = -1; break }
			}
		}.start()
	}

	fun status(): Int {
		return process
	}

}