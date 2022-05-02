package com.trdz.weather.w_view.windows

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import com.trdz.weather.w_view.*
import com.trdz.weather.x_view_model.Exchanger
import com.trdz.weather.y_model.Weather
import com.trdz.weather.z_utility.*
import com.trdz.weather.x_view_model.StatusProcess
import com.trdz.weather.x_view_model.MainViewModel
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
	private var status: Int = 0
	private var quest: Int = 0

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
		binding.BBack.setOnClickListener {
			rememberChose(-1); requireActivity().supportFragmentManager.popBackStack()
		}
		binding.recycleList.adapter = adapter
		val observer = Observer<StatusProcess> { renderData(it) }
		viewModel.getData().observe(viewLifecycleOwner, observer)
		rememberChose(coordinates)
		startSearch()
	}

	private fun rememberChose(chose: Int) {
		requireActivity().getSharedPreferences(OPTIONS_KEY, Context.MODE_PRIVATE).edit().putInt(PARAM_POS_KEY, chose).apply()
	}

	private fun startSearch() {
		if (coordinates == 0) viewModel.getWeather()
		else viewModel.getWeatherList(coordinates)
	}

	private fun renderData(data: StatusProcess) {
		when (data) {
			StatusProcess.Load -> {
				openAbout(coordinates == 0)
				Log.d("@@@", "App - start data loading")
				binding.loadingLayout.visibility = View.VISIBLE
				executors.getExecutor().showToast(requireContext(), getString(R.string.t_loading), Toast.LENGTH_SHORT)
				startTimer()
			}
			is StatusProcess.Loading -> {
				Log.d("@@@", "App - update loading stage")
				quest = data.progress

			}
			is StatusProcess.Error -> {
				status = -99
				Log.d("@@@", "App - error" + data.code)
				binding.loadingLayout.error_found.visibility = View.VISIBLE
				binding.mainView.showSnackBar(getString(R.string.t_error) + "  " + data.code + "  " + data.error, Snackbar.LENGTH_INDEFINITE) {
					action(R.string.t_repeat) {
						if (binding.loadingLayout.error_found.isChecked) {
							requireActivity().startService(Intent(requireContext(), Exchanger::class.java).apply { putExtra(SERVICE_GETTER, data.dataPast) })
							status = 100
						} else {
							status = 1
							quest = 90
							viewModel.repeat(data.dataPast)
						}
					}
				}
			}
			is StatusProcess.TransferList -> {
				Log.d("@@@", "App - get list")
				dataAnalyze(data.dataAll)
			}
			is StatusProcess.Success -> {
				Log.d("@@@", "App - success")
				dataAnalyze(data.dataCurrent)
				status = 100
			}
		}
	}

	private fun startTimer() {
		status = 0
		Thread {
			quest = 90
			do {
				Thread.sleep(5L)
				if (status > -1 && status != 100) status = Math.min(status + 1, quest - 1)
				Handler(Looper.getMainLooper()).post { binding.progressBar.text = status.toString() }
			} while (status < 100)
			Handler(Looper.getMainLooper()).post { binding.loadingLayout.visibility = View.GONE }
		}.start()
	}

	private fun dataAnalyze(data: List<Weather>) {
		adapter.setData(data)
		binding.loadingLayout.visibility = View.GONE
	}

	private fun dataAnalyze(data: Weather) {
		requireActivity().startService(Intent(requireContext(), Exchanger::class.java).apply { putExtra(SERVICE_GETTER, data) })
	}

	private fun openAbout(isFast: Boolean) {
		executors.getNavigation().add(requireActivity().supportFragmentManager, WindowDetails.newInstance(Bundle().apply {
			putBoolean(W_FAST_BUNDLE, isFast)
		}))
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

	override fun onItemClick(weather: Weather) = viewModel.getWeather(weather)

}