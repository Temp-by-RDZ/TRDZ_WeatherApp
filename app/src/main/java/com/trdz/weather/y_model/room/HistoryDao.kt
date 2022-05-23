package com.trdz.weather.y_model.room

import androidx.room.*

@Dao
interface HistoryDao {
	@Query("INSERT INTO history_table (code, city,lat,lon,temperature,feelsLike,icon) VALUES(:code,:city,:lat,:lon,:temperature,:sumare,:icon)")
	fun nativeInsert(code: Long, city: String, lat: Double, lon: Double, temperature: Int, sumare: Int, icon: String)

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(entity: HistoryEntity)

	@Delete
	fun delete(entity: HistoryEntity)

	@Update
	fun update(entity: HistoryEntity)

	@Query("SELECT * FROM history_table WHERE code=:code")
	fun getHistoryForCity(code: Long): List<HistoryEntity>

}