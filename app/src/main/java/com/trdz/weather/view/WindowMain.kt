package com.trdz.weather.view

import android.content.Context
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
import com.trdz.weather.model.Weather
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
		val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
		val observer = Observer<ApplicationStatus> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
		viewModel.initialize(requireActivity().getSharedPreferences("BIG", Context.MODE_PRIVATE))//Времееное применение для теста!!!
		viewModel.getWeather()
	}

	private fun renderData(data: ApplicationStatus) {
		when (data) {
			is ApplicationStatus.Error -> {
				binding.loadingLayout.visibility = View.GONE
				refresh(data.dataPast)
				if (data.dataPast.city.name.equals("Ошибка")) binding.coordinates.text = "Не удалось подключиться к серверам"
				else binding.coordinates.text = "Не удалось подключиться. Зугруженны последние данные."
				Snackbar.make(binding.mainView, "Ошибка загрузки: ${data.error}", Snackbar.LENGTH_INDEFINITE).show()
			}
			is ApplicationStatus.Loading -> {
				binding.progressBar.text = data.progress.toString()
			}
			is ApplicationStatus.Success -> {
				binding.loadingLayout.visibility = View.GONE
				refresh(data.dataCurrent)
				Toast.makeText(requireContext(), "Данные по LiveData - Полученны", Toast.LENGTH_SHORT).show()
			}
			ApplicationStatus.Load -> {
				binding.loadingLayout.visibility = View.VISIBLE
				Toast.makeText(requireContext(), "Данные по LiveData - Загрузка", Toast.LENGTH_SHORT).show()
			}
		}
	}

	private fun refresh(data: Weather) {
		binding.message.text = data.city.name
		binding.coordinates.text = StringBuilder("${data.city.lat} ").append("${data.city.lon}").toString()
		binding.feelsLikeValue.text = data.sumare.toString()
		binding.temperatureValue.text = data.temperature.toString()
	}

	companion object {
		fun newInstance() = WindowMain()
	}
}