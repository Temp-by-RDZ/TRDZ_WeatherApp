package com.trdz.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(val city: City = defaultCity(), val temperature:Int=-69, val sumare:Int=-69): Parcelable

@Parcelize
data class City(val name:String, val lat:Float, val lon:Float): Parcelable

fun defaultCity() = City("Атлантида",31.254167f, -24.254167f)

fun getPossibilities() :List<Weather> {
	return listOf(
		Weather(City("Атлантида",31.254167f, -24.254167f),-69,-69),
		Weather(City("Лондон", 51.5085300f, -0.1257400f), 1, 2),
		Weather(City("Токио", 35.6895000f, 139.6917100f), 3, 4),
		Weather(City("Париж", 48.8534100f, 2.3488000f), 5, 6),
		Weather(City("Берлин", 52.52000659999999f, 13.404953999999975f), 7, 8),
		Weather(City("Рим", 41.9027835f, 12.496365500000024f), 9, 10),
		Weather(City("Минск", 53.90453979999999f, 27.561524400000053f), 11, 12),
		Weather(City("Стамбул", 41.0082376f, 28.97835889999999f), 13, 14),
		Weather(City("Вашингтон", 38.9071923f, -77.03687070000001f), 15, 16),
		Weather(City("Киев", 50.4501f, 30.523400000000038f), 17, 18),
		Weather(City("Пекин", 39.90419989999999f, 116.40739630000007f), 19, 20),
		Weather(City("Москва", 55.755826f, 37.617299900000035f), 1, 2)
	)
}
