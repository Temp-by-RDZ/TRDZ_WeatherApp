package com.trdz.weather.y_model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Long,
	val code: Long,
	val city: String,
	val lat: Double,
	val lon: Double,
	val temperature: Int,
	val feelsLike: Int,
	val icon: String,
)