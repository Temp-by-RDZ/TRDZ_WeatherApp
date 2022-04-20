package com.trdz.weather.y_model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(val city: City = defaultCity(), val temperature: Int = -69, val sumare: Int = -69) : Parcelable

@Parcelize
data class City(var name: String, val lat: Double, val lon: Double) : Parcelable

fun defaultCity() = City("Атлантида", 31.254167, -24.254167)
fun currentCity() = City("Текущее местоположение", 666.0, 666.0)

fun listEurope() = listOf(
	Weather(City("Амстердам", 52.37, 4.89)),
	Weather(City("Андорра-ла-Велья", 42.51, 1.52)),
	Weather(City("Атлантида", 31.254167, -24.254167)),
	Weather(City("Афины", 37.98, 23.73)),
	Weather(City("Берлин", 52.52, 13.41)),
	Weather(City("Берн", 46.95, 7.45)),
	Weather(City("Братислава", 48.15, 17.11)),
	Weather(City("Брюссель", 50.85, 4.35)),
	Weather(City("Будапешт", 47.5, 19.04)),
	Weather(City("Бухарест", 44.43, 26.11)),
	Weather(City("Вадуц", 47.14, 9.52)),
	Weather(City("Валлетта", 35.9, 14.51)),
	Weather(City("Варшава", 52.23, 21.01)),
	Weather(City("Ватикан", 41.9, 12.45)),
	Weather(City("Вена", 48.21, 16.37)),
	Weather(City("Вильнюс", 54.69, 25.28)),
	Weather(City("Загреб", 45.81, 15.98)),
	Weather(City("Киев", 50.45, 30.52)),
	Weather(City("Кишинёв", 47.01, 28.86)),
	Weather(City("Копенгаген", 55.68, 12.57)),
	Weather(City("Лиссабон", 38.72, -9.13)),
	Weather(City("Лондон", 51.51, -0.13)),
	Weather(City("Любляна", 46.05, 14.51)),
	Weather(City("Люксембург", 49.61, 6.13)),
	Weather(City("Мадрид", 40.42, -3.7)),
	Weather(City("Минск", 53.9, 27.57)),
	Weather(City("Москва", 55.75, 37.62)),
	Weather(City("Монако", 43.73, 7.42)),
	Weather(City("Нижний Новгород", 56.33, 44.0)),
	Weather(City("Осло", 59.91, 10.75)),
	Weather(City("Париж", 48.85, 2.35)),
	Weather(City("Подгорица", 42.44, 19.26)),
	Weather(City("Прага", 50.09, 14.42)),
	Weather(City("Рейкьявик", 64.14, -21.9)),
	Weather(City("Рига", 56.95, 24.11)),
	Weather(City("Рим", 41.89, 12.51)),
	Weather(City("Сан-Марино", 43.94, 12.45)),
	Weather(City("Сараево", 43.85, 18.36)),
	Weather(City("Скопье", 42.0, 21.43)),
	Weather(City("София", 42.7, 23.32)),
	Weather(City("Стокгольм", 59.33, 18.06)),
	Weather(City("Таллинн", 59.44, 24.75)),
	Weather(City("Тирана", 41.33, 19.82)),
	Weather(City("Хельсинки", 60.17, 24.94))
)

fun listAfrica() = listOf(
	Weather(City("Абу-Даби", 24.47, 54.37)),
	Weather(City("Абуджа", 9.06, 7.5)),
	Weather(City("Аддис-Абеба", 9.02, 38.75)),
	Weather(City("Асмэра", 15.34, 38.93)),
	Weather(City("Аккра", 5.56, -0.2)),
	Weather(City("Алжир", 36.73, 3.09)),
	Weather(City("Антананариву", -18.91, 47.54)),
	Weather(City("Банги", 4.36, 18.56)),
	Weather(City("Бамако", 12.65, -8.0)),
	Weather(City("Банжул", 13.45, -16.58)),
	Weather(City("Бисау", 11.86, -15.6)),
	Weather(City("Браззавиль", -4.27, 15.28)),
	Weather(City("Бужумбура", -3.38, 29.36)),
	Weather(City("Виндхук", -22.56, 17.08)),
	Weather(City("Габороне", -24.65, 25.91)),
	Weather(City("Дакар", 14.69, -17.44)),
	Weather(City("Джибути", 11.59, 43.15)),
	Weather(City("Джуба", 4.85, 31.58)),
	Weather(City("Додома", -6.17, 35.74)),
	Weather(City("Каир", 30.06, 31.25)),
	Weather(City("Кампала", 0.32, 32.58)),
	Weather(City("Кигали", -1.95, 30.06)),
	Weather(City("Киншаса", -4.33, 15.31)),
	Weather(City("Конакри", 9.54, -13.68)),
	Weather(City("Либревиль", 0.39, 9.45)),
	Weather(City("Лилонгве", -13.97, 33.79)),
	Weather(City("Ломе", 6.13, 1.22)),
	Weather(City("Луанда", -8.84, 13.23)),
	Weather(City("Лусака", -15.41, 28.29)),
	Weather(City("Малабо", 3.76, 8.78)),
	Weather(City("Мапуту", -25.97, 32.58)),
	Weather(City("Масеру", -29.32, 27.48)),
	Weather(City("Мбабане", -26.32, 31.13)),
	Weather(City("Могадишо", 2.04, 45.34)),
	Weather(City("Монровия", 6.3, -10.8)),
	Weather(City("Морони", -11.7, 43.26)),
	Weather(City("Найроби", -1.28, 36.82)),
	Weather(City("Нджамена", 12.11, 15.04)),
	Weather(City("Ниамей", 13.51, 2.11)),
	Weather(City("Нуакшот", 18.09, -15.98)),
	Weather(City("Порто-Ново", 6.5, 2.6)),
	Weather(City("Прая", 14.93, -23.51)),
	Weather(City("Претория", -25.74, 28.19)),
	Weather(City("Рабат", 34.01, -6.83)),
	Weather(City("Сана", 15.35, 44.21)),
	Weather(City("Сан-Томе", 0.34, 6.73)),
	Weather(City("Триполи", 32.89, 13.19)),
	Weather(City("Тунис", 36.82, 10.17)),
	Weather(City("Уагадугу", 12.37, -1.53)),
	Weather(City("Фритаун", 8.49, -13.24)),
	Weather(City("Хараре", -17.83, 31.05)),
	Weather(City("Хартум", 15.55, 32.53)),
	Weather(City("Ямусукро", 6.82, -5.28)),
	Weather(City("Яунде", 3.87, 11.52))
)

fun listAsia() = listOf(
	Weather(City("Астрахань", 46.35, 48.04)),
	Weather(City("Амман", 31.96, 35.95)),
	Weather(City("Анкара", 39.92, 32.85)),
	Weather(City("Ашхабад", 37.95, 58.0)),
	Weather(City("Багдад", 33.34, 44.4)),
	Weather(City("Бейрут", 33.89, 35.5)),
	Weather(City("Барнаул", 53.36, 83.76)),
	Weather(City("Белград", 44.8, 20.47)),
	Weather(City("Брянск", 53.25, 34.37)),
	Weather(City("Владивосток", 43.11, 131.87)),
	Weather(City("Волгоград", 48.72, 44.5)),
	Weather(City("Воронеж", 51.67, 39.18)),
	Weather(City("Дамаск", 33.51, 36.29)),
	Weather(City("Дублин", 53.33, -6.25)),
	Weather(City("Екатеринбург", 56.85, 60.61)),
	Weather(City("Ереван", 40.18, 44.51)),
	Weather(City("Иваново", 57.0, 40.97)),
	Weather(City("Ижевск", 56.85, 53.2)),
	Weather(City("Иркутск", 52.3, 104.3)),
	Weather(City("Казань", 55.79, 49.12)),
	Weather(City("Калининград", 54.71, 20.51)),
	Weather(City("Кемерово", 55.33, 86.08)),
	Weather(City("Киров", 58.6, 49.66)),
	Weather(City("Краснодар", 45.04, 38.98)),
	Weather(City("Красноярск", 56.02, 92.87)),
	Weather(City("Липецк", 52.6, 39.57)),
	Weather(City("Махачкала", 42.98, 47.5)),
	Weather(City("Набережные Челны", 55.73, 52.41)),
	Weather(City("Никосия", 35.18, 33.36)),
	Weather(City("Новокузнецк", 53.76, 87.11)),
	Weather(City("Новосибирск", 55.04, 82.93)),
	Weather(City("Омск", 54.99, 73.37)),
	Weather(City("Оренбург", 51.77, 55.1)),
	Weather(City("Пенза", 53.2, 45.0)),
	Weather(City("Пермь", 58.01, 56.25)),
	Weather(City("Ростов-на-Дону", 47.23, 39.72)),
	Weather(City("Рязань", 54.63, 39.69)),
	Weather(City("Самара", 53.2, 50.15)),
	Weather(City("Санкт-Петербург", 59.94, 30.31)),
	Weather(City("Саратов", 51.54, 46.01)),
	Weather(City("Тбилиси", 41.69, 44.83)),
	Weather(City("Тольятти", 53.53, 49.35)),
	Weather(City("Томск", 56.5, 84.97)),
	Weather(City("Тула", 54.2, 37.62)),
	Weather(City("Тюмень", 57.15, 65.53)),
	Weather(City("Ульяновск", 54.33, 48.39)),
	Weather(City("Уфа", 54.74, 55.97)),
	Weather(City("Хабаровск", 48.48, 135.08)),
	Weather(City("Чебоксары", 56.13, 47.25)),
	Weather(City("Челябинск", 55.15, 61.43)),
	Weather(City("Ярославль", 57.63, 39.87)),
	Weather(City("Астана", 51.18, 71.45)),
	Weather(City("Баку", 40.38, 49.89)),
	Weather(City("Бангкок", 13.75, 100.5)),
	Weather(City("Бандар-Сери-Бегаван", 4.89, 114.94)),
	Weather(City("Бишкек", 42.87, 74.59)),
	Weather(City("Веллингтон", -41.29, 174.78)),
	Weather(City("Виктория", -4.62, 55.46)),
	Weather(City("Вьентьян", 17.97, 102.6)),
	Weather(City("Дакка", 23.71, 90.41)),
	Weather(City("Джакарта", -6.21, 106.85)),
	Weather(City("Дили", -8.56, 125.57)),
	Weather(City("Доха", 25.29, 51.53)),
	Weather(City("Душанбе", 38.54, 68.78)),
	Weather(City("Исламабад", 33.72, 73.04)),
	Weather(City("Кабул", 34.53, 69.17)),
	Weather(City("Канберра", -35.28, 149.13)),
	Weather(City("Катманду", 27.7, 85.32)),
	Weather(City("Коломбо", 6.93, 79.85)),
	Weather(City("Куала-Лумпур", 3.14, 101.69)),
	Weather(City("Маджуро", 7.09, 171.38)),
	Weather(City("Мале", 4.18, 73.51)),
	Weather(City("Манама", 26.23, 50.59)),
	Weather(City("Манила", 14.6, 120.98)),
	Weather(City("Маскат", 23.58, 58.41)),
	Weather(City("Нью-Дели", 28.64, 77.22)),
	Weather(City("Паликир", 6.92, 158.16)),
	Weather(City("Пекин", 39.91, 116.4)),
	Weather(City("Пномпень", 11.56, 104.92)),
	Weather(City("Порт-Луи", -20.16, 57.5)),
	Weather(City("Пхеньян", 39.03, 125.75)),
	Weather(City("Порт-Морсби", -9.48, 147.15)),
	Weather(City("Порт-Вила", -17.74, 168.31)),
	Weather(City("Сеул", 37.57, 126.98)),
	Weather(City("Сингапур", 1.29, 103.85)),
	Weather(City("Сува", -18.14, 178.44)),
	Weather(City("Тайбэй", 25.05, 121.53)),
	Weather(City("Ташкент", 41.26, 69.22)),
	Weather(City("Тегеран", 35.69, 51.42)),
	Weather(City("Токио", 35.69, 139.69)),
	Weather(City("Тхимпху", 27.47, 89.64)),
	Weather(City("Улан-Батор", 47.91, 106.88)),
	Weather(City("Ханой", 21.02, 105.84)),
	Weather(City("Хониара", -9.43, 159.95)),
	Weather(City("Эль-Кувейт", 29.37, 47.98)),
	Weather(City("Эр-Рияд", 24.69, 46.72)),
	Weather(City("Южная Тарава", 1.33, 172.98)),
	Weather(City("Ярен", -0.55, 166.93))
)

fun listOther() = listOf(
	Weather(City("Апиа", -13.83, -171.77)),
	Weather(City("Асунсьон", -25.29, -57.65)),
	Weather(City("Бастер", 17.3, -62.73)),
	Weather(City("Бельмопан", 17.25, -88.77)),
	Weather(City("Богота", 4.61, -74.08)),
	Weather(City("Бразилия", -15.78, -47.93)),
	Weather(City("Бриджтаун", 13.11, -59.62)),
	Weather(City("Буэнос-Айрес", -34.61, -58.38)),
	Weather(City("Вашингтон", 38.9, -77.04)),
	Weather(City("Гавана", 23.13, -82.38)),
	Weather(City("Гватемала", 14.64, -90.51)),
	Weather(City("Джорджтаун", 6.8, -58.16)),
	Weather(City("Каракас", 10.49, -66.88)),
	Weather(City("Кастри", 14.0, -61.01)),
	Weather(City("Кингстаун", 13.16, -61.23)),
	Weather(City("Кингстон", 18.0, -76.79)),
	Weather(City("Кито", -0.23, -78.52)),
	Weather(City("Лима", -12.04, -77.03)),
	Weather(City("Манагуа", 12.13, -86.25)),
	Weather(City("Мехико", 19.43, -99.13)),
	Weather(City("Монтевидео", -34.9, -56.19)),
	Weather(City("Нассау", 25.06, -77.34)),
	Weather(City("Нукуалофа", -21.14, -175.2)),
	Weather(City("Оттава", 45.41, -75.7)),
	Weather(City("Панама", 8.99, -79.52)),
	Weather(City("Парамарибо", 5.87, -55.17)),
	Weather(City("Порт-о-Пренс", 18.54, -72.34)),
	Weather(City("Порт-оф-Спейн", 10.67, -61.52)),
	Weather(City("Розо", 15.3, -61.39)),
	Weather(City("Сан-Сальвадор", 13.69, -89.19)),
	Weather(City("Сан-Хосе", 9.93, -84.08)),
	Weather(City("Санто-Доминго", 18.47, -69.89)),
	Weather(City("Сантьяго", -33.46, -70.65)),
	Weather(City("Сент-Джонс", 17.12, -61.84)),
	Weather(City("Сент-Джоржес", 12.05, -61.75)),
	Weather(City("Сукре", -19.03, -65.26)),
	Weather(City("Тегусигальпа", 14.08, -87.21)),
)