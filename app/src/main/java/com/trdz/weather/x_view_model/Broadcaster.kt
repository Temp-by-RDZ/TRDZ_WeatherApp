package com.trdz.weather.x_view_model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.trdz.weather.z_utility.SERVICE_SETTER

class Broadcaster : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			Log.d("@@@","MyBroadcastReceiver Active ${intent!!.action}")
			intent.let {
				//val extra = it.getStringExtra(SERVICE_SETTER)
				Log.d("@@@","MyBroadcastReceiver onReceive")
			}
		}
	}
