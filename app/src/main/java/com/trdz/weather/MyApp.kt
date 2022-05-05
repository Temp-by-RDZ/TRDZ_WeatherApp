package com.trdz.weather

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.trdz.weather.y_model.ServerRetrofitApi
import com.trdz.weather.y_model.ServerRetrofitDynamicApi
import com.trdz.weather.y_model.room.DataBase
import com.trdz.weather.y_model.room.HistoryDao
import com.trdz.weather.z_utility.DOMAIN
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp : Application() {

	override fun onCreate() {
		super.onCreate()
		appContext = this
	}

	companion object {
		private var db: DataBase? = null
		private var appContext: MyApp? = null
		fun getHistoryDao(): HistoryDao {
			if (db == null) {
				if (appContext != null) {
					db = Room.databaseBuilder(appContext!!, DataBase::class.java, "test").build()
				} else {
					throw IllegalStateException("Порченный appContext")
				}
			}
			return db!!.historyDao()
		}

		private var retrofitCustom: ServerRetrofitApi? = null
		private var retrofitDynamic: ServerRetrofitDynamicApi? = null

		private fun createRetrofitCustom() {
			retrofitCustom = Retrofit.Builder().apply {
				baseUrl(DOMAIN)
				addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
			}.build().create(ServerRetrofitApi::class.java)
		}

		private fun createRetrofitDynamic() {
			retrofitDynamic = Retrofit.Builder().apply {
				baseUrl(DOMAIN)
				addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
			}.build().create(ServerRetrofitDynamicApi::class.java)
		}

		fun getRetrofitCustom(): ServerRetrofitApi {
			if (retrofitCustom == null) createRetrofitCustom()
			return retrofitCustom!!
		}

		fun getRetrofitDynamic(): ServerRetrofitDynamicApi {
			if (retrofitDynamic == null) createRetrofitDynamic()
			return retrofitDynamic!!
		}

	}
}