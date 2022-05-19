package com.trdz.weather.z_utility

	import android.app.NotificationChannel
	import android.app.NotificationManager
	import android.app.PendingIntent
	import android.content.Context
	import android.content.Intent
	import android.os.Build
	import android.util.Log
	import androidx.core.app.NotificationCompat
	import com.google.firebase.messaging.FirebaseMessagingService
	import com.google.firebase.messaging.RemoteMessage
	import com.trdz.weather.R
	import com.trdz.weather.w_view.MainActivity


class GlobalNotification : FirebaseMessagingService(){

		// server - AAAADwp8_MA:APA91bGUPeT-dXfmwz3iA5txNAvRyuJncHClv6lxXNdwwYTH338jnNthZYGj5tzadUdiRvMfryUDautPJzqsYTt32czrGGxdLc-uifTc8dOwG1DZbJUvilxlQuVFqUbdKYXRovURO7N1

		override fun onMessageReceived(message: RemoteMessage) {
			Log.d("@@@", "Fir - Message acquired")
			if(!message.data.isNullOrEmpty()){
				val title = message.data[KEY_TITLE]
				val message = message.data[KEY_MESSAGE]
				if(!title.isNullOrEmpty()&&!message.isNullOrEmpty()){
					push(title,message)
				}
			}
		}

		private fun push(title:String,message:String){
			Log.d("@@@", "Fir - Message showed")
			val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			val contentIntent = PendingIntent.getActivity(this, 0,
				Intent(applicationContext, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

			val notificationBuilderHigh= NotificationCompat.Builder(this, CHANNEL_ID_LOW).apply {
				setSmallIcon(R.drawable.ic_launcher_foreground)
				setContentTitle(title)
				setContentText(message)
				setContentIntent(contentIntent)
				priority = NotificationManager.IMPORTANCE_HIGH
			}

			if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
				val channelLow = NotificationChannel(CHANNEL_ID_LOW,"Name $CHANNEL_ID_LOW",NotificationManager.IMPORTANCE_LOW).apply {
					description = "Description $CHANNEL_ID_LOW"
				}
				notificationManager.createNotificationChannel(channelLow)
			}

			notificationManager.notify(NOTIFICATION_ID_LOW,notificationBuilderHigh.build())
		}

	companion object {
		private const val NOTIFICATION_ID_LOW = 1
		private const val CHANNEL_ID_LOW = "channel_id_1"

		private const val KEY_TITLE = "myTitle"
		private const val KEY_MESSAGE = "myMessage"
	}

		override fun onNewToken(token: String) {
			super.onNewToken(token)
		}
	}