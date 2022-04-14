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
import com.trdz.weather.utility.*
import com.trdz.weather.view_model.ApplicationStatus
import com.trdz.weather.view_model.MainViewModel
import kotlinx.android.synthetic.main.fragment_window_list.view.*

class WindowList : Fragment(), ItemListClick {

	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowListBinding? = null
	private val binding get() = _binding!!
	private var _viewModel: MainViewModel? = null
	private val viewModel get() = _viewModel!!

	private val adapter = WindowListAdapter(this)
	private var coordinates: Int = 0

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
		_viewModel = null
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let { coordinates = it.getInt(W_LIST_BUNDLE) }
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowListBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		_viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.BBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
		binding.recycleList.adapter = adapter
		val observer = Observer<ApplicationStatus> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
		viewModel.initialize(requireActivity().getSharedPreferences("BIG", Context.MODE_PRIVATE))//Времееное применение для теста!!!
		startSearch()
	}

	private fun startSearch() {
		if (coordinates == 0) viewModel.getSpecifiqWeather()
		else viewModel.getWeather()
	}

	private fun renderData(data: ApplicationStatus) {
		when (data) {
			is ApplicationStatus.Error -> {
				binding.loadingLayout.error_found.visibility = View.VISIBLE
				binding.mainView.showSnackBar(getString(R.string.t_error) + "  " + data.error, Snackbar.LENGTH_INDEFINITE) {
					action(R.string.t_repeat) {
						if (binding.loadingLayout.error_found.isChecked) dataAnalyze(data.dataPast)
						else startSearch()
					}
				}
			}
			is ApplicationStatus.Loading -> {
				binding.progressBar.text = data.progress.toString()
			}
			is ApplicationStatus.Success -> {
				dataAnalyze(data.dataCurrent)
			}
			ApplicationStatus.Load -> {
				binding.loadingLayout.visibility = View.VISIBLE
				executors.getExecutor().showToast(requireContext(), getString(R.string.t_loading), Toast.LENGTH_SHORT)
			}
		}
	}

	private fun dataAnalyze(data: List<Weather>) {
		if (coordinates > 0) adapter.setData(data)
		else openAbout(data.random(), true)
		binding.loadingLayout.visibility = View.GONE
	}

	private fun openAbout(data: Weather, isFast: Boolean) {
		executors.getNavigation().add(R.id.container_fragment_base, WindowMain.newInstance(Bundle().apply {
			putBoolean(W_FAST_BUNDLE, isFast)
			putParcelable(W_MAIN_BUNDLE, data)
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

	override fun onItemClick(weather: Weather) = openAbout(weather, false)

}