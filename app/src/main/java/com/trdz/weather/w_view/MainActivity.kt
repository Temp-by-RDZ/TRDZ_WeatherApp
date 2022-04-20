package com.trdz.weather.w_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trdz.weather.R
import com.trdz.weather.w_view.windows.WindowStart

class MainActivity : AppCompatActivity(), Leader {

	private val navigation = Navigation(R.id.container_fragment_base)
	private val executor = Executor()

	override fun onDestroy() {
		executor.stop()
		super.onDestroy()
	}

	override fun onBackPressed() { /*nothing*/ }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (savedInstanceState == null) navigation.add(supportFragmentManager, WindowStart.newInstance(), false)
	}

	override fun getNavigation() = navigation
	override fun getExecutor() = executor

}