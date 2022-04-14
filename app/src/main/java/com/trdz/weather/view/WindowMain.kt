package com.trdz.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.weather.databinding.FragmentWindowMainBinding
import com.trdz.weather.model.Weather
import com.trdz.weather.utility.W_FAST_BUNDLE
import com.trdz.weather.utility.W_MAIN_BUNDLE
import java.lang.StringBuilder

class WindowMain : Fragment() {

	private var _binding: FragmentWindowMainBinding? = null
	private val binding get() = _binding!!
	private var repeatable: Boolean = false

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowMainBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.BBack.setOnClickListener { fallBack() }
		arguments?.run {
			getBoolean(W_FAST_BUNDLE).let { repeatable = it }
			getParcelable<Weather>(W_MAIN_BUNDLE)?.let { renderData(it) }
		}
	}

	private fun fallBack() {
		requireActivity().supportFragmentManager.popBackStack()
		if (repeatable) requireActivity().supportFragmentManager.popBackStack()
	}

	private fun renderData(data: Weather) {
		with(binding) {
			message.text = data.city.name
			coordinates.text = StringBuilder("${data.city.lat} ").append("${data.city.lon}").toString()
			feelsLikeValue.text = data.sumare.toString()
			temperatureValue.text = data.temperature.toString()
		}
	}

	companion object {
		@JvmStatic
		fun newInstance(bundle: Bundle): WindowMain {
			val argument = WindowMain()
			argument.arguments = bundle
			return argument
		}
	}
}