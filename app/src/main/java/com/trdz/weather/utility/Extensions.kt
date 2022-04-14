package com.trdz.weather.utility

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: Int, length: Int = Snackbar.LENGTH_LONG) {
	showSnackBar(resources.getString(message), length)
}

fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG) {
	Snackbar.make(this, message, length).show()
}

inline fun View.showSnackBar(message: Int, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	showSnackBar(resources.getString(message), length, action)
}

inline fun View.showSnackBar(message: String, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	val snackbar =	Snackbar.make(this, message, length)
	snackbar.action()
	snackbar.show()
}






inline fun View.snack(message: Int, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	showSnackBar(resources.getString(message), length, action)
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit) {
	val snack = Snackbar.make(this, message, length)
	snack.action()
	snack.show()
}

fun Snackbar.action(action: Int, color: Int? = null, listener: (View) -> Unit) {
	action(view.resources.getString(action), color, listener)
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
	setAction(action, listener)
	color?.let { setActionTextColor(color) }
}