package com.trdz.weather.w_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.trdz.weather.R
import com.trdz.weather.w_view.fragments_windows.WindowList
import com.trdz.weather.w_view.fragments_windows.WindowStart

class MainActivity : AppCompatActivity(), Leader {

	//region Elements
	private val navigation = Navigation(R.id.container_fragment_base)
	private val executor = Executor()

	//endregion

	//region Base realization
	override fun onDestroy() {
		executor.stop()
		super.onDestroy()
	}

	override fun onBackPressed() { /*nothing*/
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		menuConstruct()
		if (savedInstanceState == null) navigation.replace(supportFragmentManager, WindowStart.newInstance(), false)
		getToken()
	}

	//endregion

	//region Menu realization
	private fun getToken() {
		FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
			if (!task.isSuccessful) {
				Log.w("@@@", "Fetching FCM registration token failed", task.exception)
				return@OnCompleteListener
			}
			val token = task.result
			Log.d("@@@", "User token - $token")
		})
	}

	private fun menuConstruct() {
		setSupportActionBar(findViewById(R.id.toolbar))
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_geo, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.open_geolocation) openGeo()
		return super.onOptionsItemSelected(item)
	}

	//endregion

	//region Main functional
	private fun openGeo() {
		navigation.returnTo(supportFragmentManager)
		navigation.add(supportFragmentManager, WindowList.newInstance(-1))
	}
	//endregion

	override fun getNavigation() = navigation
	override fun getExecutor() = executor

}