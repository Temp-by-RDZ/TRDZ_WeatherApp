package com.trdz.weather.w_view.windows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.trdz.weather.databinding.FragmentWindowLocationBinding
import com.trdz.weather.w_view.Leader
import com.trdz.weather.w_view.MainActivity
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.trdz.weather.z_utility.REQUEST_CODE


class WindowLocation : Fragment() {

	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowLocationBinding? = null
	private val binding get() = _binding!!

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowLocationBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		checkPermission()
		buttonBinds()
	}

	private fun checkPermission() {
		when {ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
		PackageManager.PERMISSION_GRANTED -> { getLocation() }
		shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> { explain() }
		else -> { permissionGranting() }
		}
	}

	private fun explain() {
		AlertDialog.Builder(requireContext())
			.setTitle("Требуется подтверждение")
			.setMessage(" Для вывода погоды по геолокации нужен доступ к геолокации")
			.setPositiveButton("Подтвердить") { _, _ -> permissionGranting() }
			.setNegativeButton("Запретить") { dialog, _ -> dialog.dismiss() }
			.create()
			.show()
	}


	private fun permissionGranting() {
		requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray,) {
		if (requestCode == REQUEST_CODE) {
			for (i in permissions.indices) {
				if (permissions[i] == Manifest.permission.ACCESS_FINE_LOCATION && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
					getLocation()
				} else {
					explain()
				}
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		}
	}

	@SuppressLint("MissingPermission")
	private fun getLocation() {
		context?.let {
			val locationManager = it.getSystemService(Context.LOCATION_SERVICE) as LocationManager
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				val providerGPS = locationManager.getProvider(LocationManager.GPS_PROVIDER)
				providerGPS?.let {
					locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER,
						0,
						100f,
						locationListenerDistance
					)
				}
			}
		}
	}

	private val locationListenerDistance = object : LocationListener {
		override fun onLocationChanged(location: Location) {
			Log.d("@@@", location.toString())
			getAddressByLocation(location)
		}

		override fun onProviderDisabled(provider: String) {
			super.onProviderDisabled(provider)
		}

		override fun onProviderEnabled(provider: String) {
			super.onProviderEnabled(provider)
		}

	}

	fun getAddressByLocation(location: Location) {
		val geocoder = Geocoder(requireContext())
		val timeStump = System.currentTimeMillis()
		Thread {
			val addressText = geocoder.getFromLocation(location.latitude, location.longitude, 1000000)[0].getAddressLine(0)
			requireActivity().runOnUiThread {
				showAddressDialog(addressText, location)
			}
		}.start()
		Log.d("@@@", " прошло ${System.currentTimeMillis() - timeStump}")
	}

	private fun showAddressDialog(address: String, location: Location) {
		activity?.let {
			AlertDialog.Builder(it)
				.setTitle("Местоположение определенно")
				.setMessage(address)
				.setPositiveButton("Открыть погоду") { _, _ ->
								/*address,
								location.latitude,
								location.longitude
								 */
				}
				.setNegativeButton("Вернуться") { dialog, _ -> dialog.dismiss(); executors.restartWork() }
				.create()
				.show()
		}
	}


private fun buttonBinds() {}

companion object { fun newInstance() = WindowLocation() }
}