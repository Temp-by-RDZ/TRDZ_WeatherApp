package com.trdz.weather.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Navigation(private val manager: FragmentManager) {
	fun is_corrupted(): Boolean {
		return manager.isDestroyed
	}

	fun add(container: Int, fragment: Fragment?, remember: Boolean) {
		if (manager.isDestroyed) return
		val transaction = manager.beginTransaction()
		transaction.add(container, fragment!!)
		if (remember) transaction.addToBackStack("")
		transaction.commit()
	}

	fun replace(container: Int, fragment: Fragment?, remember: Boolean) {
		if (manager.isDestroyed) return
		val transaction = manager.beginTransaction()
		transaction.replace(container, fragment!!)
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		if (remember) transaction.addToBackStack("")
		transaction.commit()
	}
}