package com.trdz.weather.w_view.fragments_windows

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
import com.trdz.weather.w_view.Leader
import com.trdz.weather.w_view.MainActivity
import com.trdz.weather.y_model.Weather
import com.trdz.weather.z_utility.SERVICE_BROAD
import com.trdz.weather.z_utility.SERVICE_SETTER
import com.trdz.weather.z_utility.W_FAST_BUNDLE
import com.trdz.weather.z_utility.loadSvg

class WindowDetails : Fragment() {

	//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowDetailsBinding? = null
	private val binding get() = _binding!!
	private var repeatable: Boolean = false

	//endregion

	//region Base realization
	private val receiver = object : BroadcastReceiver() {
		override fun onReceive(context: Context?, intent: Intent?) {
			intent?.getParcelableExtra<Weather>(SERVICE_SETTER)?.let { renderData(it) }
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
		LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowDetailsBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
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

	//endregion

	//region Main functional
	private fun fallBack() {
		if (repeatable) executors.getNavigation().returnTo(requireActivity().supportFragmentManager)
		else requireActivity().supportFragmentManager.popBackStack()
	}

	private fun renderData(data: Weather) {
		with(binding) {
			message.text = data.city.name
			coordinates.text = StringBuilder("${data.city.lat} ").append("${data.city.lon}").toString()
			feelsLikeValue.text = data.sumare.toString()
			temperatureValue.text = data.temperature.toString()
			details.visibility = View.VISIBLE
			icon.loadSvg("https://yastatic.net/weather/i/icons/blueye/color/svg/${data.icon}.svg")
		}
	}

	//endregion

	companion object {
		@JvmStatic
		fun newInstance(bundle: Bundle): WindowDetails {
			val argument = WindowDetails()
			argument.arguments = bundle
			return argument
		}
	}
}