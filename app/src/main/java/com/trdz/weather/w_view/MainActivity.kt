package com.trdz.weather.w_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.trdz.weather.R
import com.trdz.weather.w_view.windows.WindowStart
import com.trdz.weather.w_view.windows.WindowLocation

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
		menuConstruct()
		if (savedInstanceState == null) navigation.add(supportFragmentManager, WindowStart.newInstance(), false)

	}

	private fun menuConstruct() {
		setSupportActionBar(findViewById(R.id.toolbar))

		val drawer = findViewById<DrawerLayout>(R.id.drawer)
		val toggle = ActionBarDrawerToggle(this, drawer, findViewById(R.id.toolbar), R.string.ND_OPEN, R.string.ND_CLOSE)
		drawer.addDrawerListener(toggle)
		toggle.syncState()
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_geo, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.open_geolocation) openGeo()
		return super.onOptionsItemSelected(item)
	}

	private fun openGeo() {
		navigation.replace(supportFragmentManager, WindowLocation.newInstance(), false)
	}

	override fun restartWork() {navigation.replace(supportFragmentManager, WindowStart.newInstance(), false) }
	override fun getNavigation() = navigation
	override fun getExecutor() = executor

}