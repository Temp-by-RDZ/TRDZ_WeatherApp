package com.trdz.weather.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.trdz.weather.R
import com.trdz.weather.databinding.FragmentWindowListBinding
import com.trdz.weather.model.Weather
import com.trdz.weather.utility.W_FAST_BUNDLE
import com.trdz.weather.utility.W_LIST_BUNDLE
import com.trdz.weather.utility.W_MAIN_BUNDLE
import com.trdz.weather.view_model.ApplicationStatus
import com.trdz.weather.view_model.MainViewModel

class WindowList : Fragment(),ItemListClick {

	private var _binding: FragmentWindowListBinding? = null
	private val binding get() = _binding!!
	private val adapter = WindowListAdapter(this)
	private var coordinates: Int = 0

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let { coordinates = it.getInt(W_LIST_BUNDLE) }
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowListBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.BBack.setOnClickListener{requireActivity().onBackPressed()}
		binding.recycleList.adapter = adapter
		val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
		val observer = Observer<ApplicationStatus> {  renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
		viewModel.initialize(requireActivity().getSharedPreferences("BIG", Context.MODE_PRIVATE))//Времееное применение для теста!!!
		if (coordinates == 0) viewModel.getSpecifiqWeather()
		else viewModel.getWeather()
	}

	private fun renderData(data: ApplicationStatus) {
		when (data) {
			is ApplicationStatus.Error -> {
				Snackbar.make(binding.mainView, "Ошибка загрузки: ${data.error}", Snackbar.LENGTH_LONG).show()
				dataAnalyze(data.dataPast)
			}
			is ApplicationStatus.Loading -> {
				binding.progressBar.text = data.progress.toString()
			}
			is ApplicationStatus.Success -> {
				Toast.makeText(requireContext(), "Данные по LiveData - Полученны", Toast.LENGTH_SHORT).show()
				dataAnalyze(data.dataCurrent)
			}
			ApplicationStatus.Load -> {
				binding.loadingLayout.visibility = View.VISIBLE
				Toast.makeText(requireContext(), "Данные по LiveData - Загрузка", Toast.LENGTH_SHORT).show()
			}
		}
	}

	private fun dataAnalyze(data: List<Weather>) {
		if (coordinates > 0) adapter.setData(data)
		else openAbout(data.random(),true)
		binding.loadingLayout.visibility = View.GONE
	}

	private fun openAbout(data: Weather,isFast: Boolean) {
		(requireActivity() as MainActivity).getNavigation().add(R.id.container_fragment_base, WindowMain.newInstance(Bundle().apply {
			putBoolean(W_FAST_BUNDLE,isFast)
			putParcelable(W_MAIN_BUNDLE,data)
		}), true)
	}

	companion object {
		@JvmStatic
		fun newInstance(coordinates: Int) =
			WindowList().apply {
				arguments = Bundle().apply {
					putInt(W_LIST_BUNDLE, coordinates)
				}
			}
	}

	override fun onItemClick(weather: Weather)= openAbout(weather,false)

}