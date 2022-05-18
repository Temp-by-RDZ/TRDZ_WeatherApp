package com.trdz.weather.w_view.windows

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.trdz.weather.R
import com.trdz.weather.databinding.FragmentWindowMapsMainBinding
import com.trdz.weather.z_utility.ERROR_NUMBER
import androidx.lifecycle.ViewModelProvider
import com.trdz.weather.x_view_model.MainViewModel
import com.trdz.weather.y_model.City
import com.trdz.weather.y_model.Weather


class WindowMaps : Fragment() {

	private lateinit var map: GoogleMap
	private var _binding: FragmentWindowMapsMainBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!
	private var lastLat: Double = ERROR_NUMBER
	private var lastLng: Double = ERROR_NUMBER

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
		map = googleMap
		map.setOnMapLongClickListener {map.clear(); setMarker(it) }
		val sydney = LatLng(-34.0, 151.0)
		googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
		_viewModel = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowMapsMainBinding.inflate(inflater, container, false)
		_viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
		mapFragment?.getMapAsync(callback)
		binding.BOpen.setOnClickListener { viewModel.boo(Weather(City("Ваша позиция",lastLat,lastLng))); requireActivity().supportFragmentManager.popBackStack()}
		binding.BBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack(); requireActivity().supportFragmentManager.popBackStack() }
	}

	private fun setMarker(location: LatLng, search: String = ""): Marker {
		lastLat = location.latitude
		lastLng = location.longitude
		return map.addMarker(MarkerOptions().position(location).title(search))!! //.icon(BitmapDescriptorFactory.fromResource(resourceId)
	}
}