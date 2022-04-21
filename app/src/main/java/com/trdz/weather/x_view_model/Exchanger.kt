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
		// Rep -S-> ViewMain не подходит там уже обсервер с возможностью рестарта и без ухода с фрагмента
		// Rep -S-> ViewModel не подходит VM придерживается безконтекстного режима
		// ViewList -S-> ViewMain? или ViewList -S-> ViewList?
		intent?.let {
			val weather = it.getParcelableExtra<Weather>(SERVICE_GETTER)
			val message = Intent(SERVICE_BROAD)
			message.putExtra(SERVICE_SETTER, weather)
			LocalBroadcastManager.getInstance(this).sendBroadcast(message)
		}
	}
}