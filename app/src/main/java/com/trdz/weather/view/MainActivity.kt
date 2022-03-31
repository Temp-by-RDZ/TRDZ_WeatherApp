package com.trdz.weather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trdz.weather.R

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val navigation = Navigation(supportFragmentManager)

		if (savedInstanceState == null) navigation.add(R.id.container_fragment_base, WindowMain.newInstance(), false);
	}

}