package com.trdz.weather.w_view.windows

import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.trdz.weather.R
import com.trdz.weather.databinding.FragmentWindowMapsMainBinding
import com.trdz.weather.z_utility.ERROR_NUMBER
import androidx.lifecycle.ViewModelProvider
import com.trdz.weather.w_view.Leader
import com.trdz.weather.w_view.MainActivity
import com.trdz.weather.x_view_model.MainViewModel
import com.trdz.weather.y_model.City
import com.trdz.weather.y_model.Weather
import com.trdz.weather.z_utility.W_MAP_L_BUNDLE
import com.trdz.weather.z_utility.W_MAP_T_BUNDLE
import java.util.*

class WindowMaps : Fragment() {

//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowMapsMainBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	private lateinit var map: GoogleMap
	private var lastLat: Double = ERROR_NUMBER
	private var lastLon: Double = ERROR_NUMBER

//endregion

//region Base realization
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
		_executors = null
		_viewModel = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			lastLat = it.getDouble(W_MAP_T_BUNDLE)
			lastLon = it.getDouble(W_MAP_L_BUNDLE)
		}
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowMapsMainBinding.inflate(inflater, container, false)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
		mapFragment?.getMapAsync(callback)
		buttonBinds()
	}

	private val callback = OnMapReadyCallback { googleMap ->
		/**
		 * Manipulates the map once available.
		 * This callback is triggered when the map is ready to be used.
		 * This is where we can add markers or lines, add listeners or move the camera.
		 * In this case, we just add a marker near Sydney, Australia.
		 * If Google Play services is not installed on the device, the user will be prompted to
		 * install it inside the SupportMapFragment. This method will only be triggered once the
		 * user has installed Google Play services and returned to the app.
		 */
		val start = LatLng(lastLat, lastLon)
		map = googleMap
		map.run {
			setOnMapLongClickListener { clear(); setMarker(it) }
			addMarker(MarkerOptions().position(start).title(getString(R.string.t_title_your_position)))
			moveCamera(CameraUpdateFactory.newLatLng(start))
		}
	}

//endregion

//region Main functional
	private fun buttonBinds() {
		with(binding) {
			BOpen.setOnClickListener { prepareWeather() }
			BBack.setOnClickListener { executors.getNavigation().returnTo(requireActivity().supportFragmentManager) }
			bSearch.setOnClickListener { findLocation() }
		}
	}

	private fun prepareWeather() {
		requireActivity().supportFragmentManager.popBackStack()
		val geocoder = Geocoder(requireContext(), Locale.getDefault())
		val addressText = geocoder.getFromLocation(lastLat, lastLon, 1000000)[0].locality
		viewModel.analyzeMap(Weather(City(addressText,lastLat,lastLon)))
	}

	private fun findLocation() {
		val locName:String = binding.searchAddress.text.toString()
		val geocoder = Geocoder(requireContext())
		val result = geocoder.getFromLocationName(locName,1)
		if (result.isNotEmpty()) {
			val location = LatLng(
				result[0].latitude,
				result[0].longitude
			)
			setMarker(location)
			map.moveCamera(CameraUpdateFactory.newLatLng(location))
		}
		else executors.getExecutor().showToast(requireContext(),"Локация не найдена. Уточните")
	}

	private fun setMarker(location: LatLng, search: String = ""): Marker {
		lastLat = location.latitude
		lastLon = location.longitude
		return map.addMarker(MarkerOptions().position(location).title(search))!!
	}

//endregion

	companion object {
		@JvmStatic
		fun newInstance(lat: Double, lon: Double) =
			WindowMaps().apply {
				arguments = Bundle().apply {
					putDouble(W_MAP_T_BUNDLE, lat)
					putDouble(W_MAP_L_BUNDLE, lon)
				}
			}
	}

}