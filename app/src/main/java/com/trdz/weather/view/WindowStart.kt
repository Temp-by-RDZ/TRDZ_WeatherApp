package com.trdz.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.weather.R
import com.trdz.weather.databinding.FragmentWindowStartBinding

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
		binding.BStartMe.setOnClickListener(this)
		binding.BStartAsia.setOnClickListener(this)
		binding.BStartAfrica.setOnClickListener(this)
		binding.BStartEurope.setOnClickListener(this)
		binding.BStartOther.setOnClickListener(this)
	}

	private fun getCord(type: View?): Int {
		when (type?.id) {
			(R.id.B_Start_Asia) -> {
				return 1
			}
			(R.id.B_Start_Africa) -> {
				return 2
			}
			(R.id.B_Start_Europe) -> {
				return 3
			}
			(R.id.B_Start_Other) -> {
				return 4
			}
		}
		return 0
	}

	companion object {
		fun newInstance() = WindowStart()
	}

	override fun onClick(type: View?) {
		executors.getNavigation().add(R.id.container_fragment_base, WindowList.newInstance(getCord(type)), true)
	}
}