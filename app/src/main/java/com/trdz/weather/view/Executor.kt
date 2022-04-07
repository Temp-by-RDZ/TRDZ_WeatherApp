package com.trdz.weather.view

import android.content.Context
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class Executor() {

	private var toast: Toast? = null
	private var snackbar: Snackbar? = null

	fun stop() {
		toast?.cancel()
	}

	fun showToast(context: Context, text: String?, length: Int) {
		toast?.cancel()
		toast = Toast.makeText(context, text, length)
		toast?.show()
	}

}