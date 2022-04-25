package com.trdz.weather.x_view_model

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.trdz.weather.y_model.Weather
import com.trdz.weather.z_utility.SERVICE_BROAD
import com.trdz.weather.z_utility.SERVICE_GETTER
import com.trdz.weather.z_utility.SERVICE_SETTER

class Exchanger(val name: String = "") : IntentService(name) {
	override fun onHandleIntent(intent: Intent?) {
		/*
		Не совсем ясно преимущество добавления данного подхода в поставленную задачу
		 - ViewModel -S-> Rep 		VM придерживается безконтекстного режима
		 - Rep -S-> ?				никакого контескта в репозитории быть не должно по этому нельзя
		 - ViewList -S-> ViewList 	так будет разделен репозиторий и данные с сервера который хорошо было бы обработать
		 - ViewList -S-> ViewMain?	Это похоже на вариант - обмен между фрагментами без видимх переходов
		 Или лучше было бы перестроить структуру под работу с Серивисом?
		 */
		intent?.let {
			Log.d("@@@", "Service transfer data")
			val weather = it.getParcelableExtra<Weather>(SERVICE_GETTER)
			LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(SERVICE_BROAD).putExtra(SERVICE_SETTER,weather))
		}
	}
}