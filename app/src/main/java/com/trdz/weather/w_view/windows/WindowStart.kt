package com.trdz.weather.w_view.windows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.weather.R
import com.trdz.weather.databinding.FragmentWindowStartBinding
import com.trdz.weather.w_view.Leader
import com.trdz.weather.w_view.MainActivity

class WindowStart : Fragment(), View.OnClickListener {

	private var _executors: Leader? = null
	private val executors get() = _executors!!
	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!

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
	}

	private fun buttonBinds() {
		binding.bStartMe.setOnClickListener(this)
		binding.bStartAsia.setOnClickListener(this)
		binding.bStartAfrica.setOnClickListener(this)
		binding.bStartEurope.setOnClickListener(this)
		binding.bStartOther.setOnClickListener(this)
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

	companion object {
		fun newInstance() = WindowStart()
	}

	override fun onClick(type: View?) {
		executors.getNavigation().add(requireActivity().supportFragmentManager, WindowList.newInstance(getCord(type)))
	}
}