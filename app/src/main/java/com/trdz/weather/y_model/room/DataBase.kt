package com.trdz.weather.y_model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class], version = 1)
abstract class DataBase: RoomDatabase() {
	abstract fun historyDao(): HistoryDao
}