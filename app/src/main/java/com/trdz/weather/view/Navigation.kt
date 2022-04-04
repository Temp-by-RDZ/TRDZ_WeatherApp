package com.trdz.weather.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Navigation(private var manager: FragmentManager) {

	fun add(container: Int, fragment: Fragment?, remember: Boolean) {
		if (is_corrupted()) return
		val transaction = manager.beginTransaction()
		transaction.add(container, fragment!!)
		if (remember) transaction.addToBackStack("")
		transaction.commit()
	}

	fun replace(container: Int, fragment: Fragment?, remember: Boolean) {
		if (is_corrupted()) return
		val transaction = manager.beginTransaction()
		transaction.replace(container, fragment!!)
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		if (remember) transaction.addToBackStack("")
		transaction.commit()
	}

	fun restructorization( manager: FragmentManager) {
		if (!is_corrupted()) return
		this.manager = manager
	}

	fun is_corrupted(): Boolean {
		return manager.isDestroyed
	}
}