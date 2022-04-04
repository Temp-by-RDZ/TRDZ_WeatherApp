package com.trdz.weather.model

data class Weather(val city: City = defaultCity(), val temperature:Int=-69, val sumare:Int=-69)

data class City(val name:String, val lat:Double, val lon:Double)

fun defaultCity() = City("Атлантида",31.254167, -24.254167)
