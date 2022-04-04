package com.trdz.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.weather.databinding.FragmentWindowMainBinding
import com.trdz.weather.model.Weather
import com.trdz.weather.utility.W_MAIN_BUNDLE
import java.lang.StringBuilder

class WindowMain : Fragment() {

	private var _binding: FragmentWindowMainBinding? = null
	private val binding get() = _binding!!

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
		val weather: Weather = requireArguments().getParcelable<Weather>(W_MAIN_BUNDLE)!!
		binding.BBack.setOnClickListener{requireActivity().onBackPressed()}
		renderData(weather)
	}

	private fun renderData(data: Weather) {
		binding.message.text = data.city.name
		binding.coordinates.text = StringBuilder("${data.city.lat} ").append("${data.city.lon}").toString()
		binding.feelsLikeValue.text = data.sumare.toString()
		binding.temperatureValue.text = data.temperature.toString()
	}

	companion object {
		@JvmStatic
		fun newInstance(bundle: Bundle):WindowMain{
			val argument = WindowMain()
			argument.arguments = bundle
			return argument
		}
	}
}