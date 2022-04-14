package com.trdz.weather.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.trdz.weather.R

class MainActivity : AppCompatActivity(), Leader {

	private val navigation = Navigation(supportFragmentManager)
	private val executor = Executor()

	override fun onDestroy() {
		executor.stop()
		super.onDestroy()
	}

	override fun onBackPressed() {
		// do nothing
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (savedInstanceState == null) navigation.add(R.id.container_fragment_base, WindowStart.newInstance(), false)
		else navigation.restructorization(supportFragmentManager)
	}

	override fun getNavigation() = navigation
	override fun getExecutor() = executor

}