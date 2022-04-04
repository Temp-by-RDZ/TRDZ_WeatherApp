package com.trdz.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.trdz.weather.view_model.ApplicationStatus
import com.trdz.weather.view_model.MainViewModel
import com.trdz.weather.databinding.FragmentWindowMainBinding

class WindowMain : Fragment() {

	private var _binding: FragmentWindowMainBinding? = null
	private val binding get() = _binding!!

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentWindowMainBinding.inflate(inflater, container, false)
		return binding.root

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
		val observer = Observer<ApplicationStatus> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
		viewModel.getWeather()
	}

	private fun renderData(data: ApplicationStatus) {
		when (data) {
			is ApplicationStatus.Error -> {
				binding.loadingLayout.visibility = View.GONE
				binding.message.text = "ОШИБКА"
				binding.coordinates.text = "Не удалось подключиться к серверам"
				Snackbar.make(binding.mainView, "Ошибка загрузки: ${data.error}", Snackbar.LENGTH_INDEFINITE).show()
			}
			is ApplicationStatus.Loading -> {
				binding.progressBar.text = data.progress.toString()
			}
			is ApplicationStatus.Success -> {
				binding.loadingLayout.visibility = View.GONE
				binding.message.text = data.dataCurrent.city.name
				binding.coordinates.text = "${data.dataCurrent.city.lat}  ${data.dataCurrent.city.lon}"
				binding.feelsLikeValue.text = data.dataCurrent.sumare.toString()
				binding.temperatureValue.text = data.dataCurrent.temperature.toString()
				Toast.makeText(requireContext(),"Данные по LiveData - Полученны", Toast.LENGTH_SHORT).show()
			}
			ApplicationStatus.Load -> {
				binding.loadingLayout.visibility = View.VISIBLE
				Toast.makeText(requireContext(), "Данные по LiveData - Загрузка", Toast.LENGTH_SHORT).show()
			}
		}
	}

	companion object {
		fun newInstance() = WindowMain()
	}
}