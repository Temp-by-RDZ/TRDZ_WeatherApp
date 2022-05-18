package com.trdz.weather.w_view.windows

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.weather.R
import com.trdz.weather.databinding.FragmentWindowStartBinding
import com.trdz.weather.w_view.Leader
import com.trdz.weather.w_view.MainActivity
import com.trdz.weather.z_utility.OPTIONS_KEY
import com.trdz.weather.z_utility.PARAM_POS_KEY

class WindowStart : Fragment(), View.OnClickListener {

//region Elements
	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!
//endregion

//region Base realization
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		_executors = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowStartBinding.inflate(inflater, container, false)
		_executors = (requireActivity() as MainActivity)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
		goToLastPosition()
	}

//endregion

//region Main functional
	private fun buttonBinds() {
		binding.bStartMe.setOnClickListener(this)
		binding.bStartAsia.setOnClickListener(this)
		binding.bStartAfrica.setOnClickListener(this)
		binding.bStartEurope.setOnClickListener(this)
		binding.bStartOther.setOnClickListener(this)
	}

	private fun goToLastPosition() {
		val waypoint = requireActivity().getSharedPreferences(OPTIONS_KEY,Context.MODE_PRIVATE).getInt(PARAM_POS_KEY,-2)
		if (waypoint>-2) executors.getNavigation().add(requireActivity().supportFragmentManager, WindowList.newInstance(waypoint))
	}

	private fun getCord(type: View?): Int {
		when (type?.id) {
			(R.id.b_start_asia) -> { return 1 }
			(R.id.b_start_africa) -> { return 2 }
			(R.id.b_start_europe) -> { return 3 }
			(R.id.b_start_other) -> { return 4 }
		}
		return 0
	}
//endregion

	override fun onClick(type: View?) {
		executors.getNavigation().add(requireActivity().supportFragmentManager, WindowList.newInstance(getCord(type)))
	}

	companion object {
		fun newInstance() = WindowStart()
	}

}