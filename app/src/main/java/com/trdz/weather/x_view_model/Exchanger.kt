package com.trdz.weather.x_view_model

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.trdz.weather.y_model.Weather
import com.trdz.weather.z_utility.SERVICE_BROAD
import com.trdz.weather.z_utility.SERVICE_GETTER
import com.trdz.weather.z_utility.SERVICE_SETTER

class Exchanger(val name: String = "") : IntentService(name) {
	override fun onHandleIntent(intent: Intent?) {
		intent?.let {
			val weather = it.getParcelableExtra<Weather>(SERVICE_GETTER)
			LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(SERVICE_BROAD).putExtra(SERVICE_SETTER,weather))
		}
	}
}