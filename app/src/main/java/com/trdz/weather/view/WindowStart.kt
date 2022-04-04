package com.trdz.weather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.trdz.weather.R
import com.trdz.weather.databinding.FragmentWindowStartBinding

class WindowStart : Fragment(){

	private var _binding: FragmentWindowStartBinding? = null
	private val binding get() = _binding!!

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = FragmentWindowStartBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		buttonBinds()
	}

	private fun buttonBinds() {
		binding.BStartMe.setOnClickListener {
			(requireActivity() as MainActivity).getNavigation().add(R.id.container_fragment_base, WindowList.newInstance(0), true)}
		binding.BStartAsia.setOnClickListener {
			(requireActivity() as MainActivity).getNavigation().add(R.id.container_fragment_base, WindowList.newInstance(1), true)}
		binding.BStartAfrica.setOnClickListener {
			(requireActivity() as MainActivity).getNavigation().add(R.id.container_fragment_base, WindowList.newInstance(2), true)}
		binding.BStartEurope.setOnClickListener {
			(requireActivity() as MainActivity).getNavigation().add(R.id.container_fragment_base, WindowList.newInstance(3), true)}
		binding.BStartOther.setOnClickListener {
			(requireActivity() as MainActivity).getNavigation().add(R.id.container_fragment_base, WindowList.newInstance(4), true)}
	}

	companion object {
		fun newInstance() = WindowStart()
	}
}