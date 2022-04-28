package com.trdz.weather

import android.app.Application
import androidx.room.Room
import com.trdz.weather.y_model.room.DataBase
import com.trdz.weather.y_model.room.HistoryDao

class MyApp: Application() {
	override fun onCreate() {
		super.onCreate()
		appContext = this
	}

	companion object{
		private var db:DataBase?=null
		private var appContext:MyApp?=null
		fun getHistoryDao(): HistoryDao {
			if(db==null){
				if(appContext!=null){
					db = Room.databaseBuilder(appContext!!,DataBase::class.java,"test").build()
				}else{
					throw IllegalStateException("Порченный appContext")
				}
			}
			return db!!.historyDao()
		}
	}
}