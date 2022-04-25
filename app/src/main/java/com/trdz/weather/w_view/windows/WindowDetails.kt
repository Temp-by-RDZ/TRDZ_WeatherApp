package com.trdz.weather.w_view.windows

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.trdz.weather.databinding.FragmentWindowDetailsBinding
import com.trdz.weather.y_model.Weather
import com.trdz.weather.z_utility.SERVICE_BROAD
import com.trdz.weather.z_utility.SERVICE_SETTER
import com.trdz.weather.z_utility.W_FAST_BUNDLE
import com.trdz.weather.z_utility.loadSvg

class WindowDetails : Fragment() {

	private var _binding: FragmentWindowDetailsBinding? = null
	private val binding get() = _binding!!
	private var repeatable: Boolean = false

	private val receiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			intent?.getParcelableExtra<Weather>(SERVICE_SETTER)?.let { renderData(it) }
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowDetailsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.BBack.setOnClickListener { fallBack() }
		arguments?.run {
			getBoolean(W_FAST_BUNDLE).let { repeatable = it }
		}
		LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter(SERVICE_BROAD)
		)
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
			details.visibility = View.VISIBLE
			/*
			Glide.with(requireContext()).load("https://freepngimg.com/thumb/city/36275-3-city-hd.png").into(headerIcon)
			Picasso.get()?.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")?.into(headerIcon)
			headerCityIcon.load("https://freepngimg.com/thumb/city/36275-3-city-hd.png")*/

			icon.loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${data.icon}.svg")
		}
	}

	companion object {
		@JvmStatic
		fun newInstance(bundle: Bundle): WindowDetails {
			val argument = WindowDetails()
			argument.arguments = bundle
			return argument
		}
	}
}