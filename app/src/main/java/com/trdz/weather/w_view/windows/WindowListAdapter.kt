package com.trdz.weather.w_view.windows

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trdz.weather.databinding.FragmentWindowListItemBinding
import com.trdz.weather.y_model.Weather

class WindowListAdapter(
	private val onItemClick: ItemListClick,
	private var data: List<Weather> = listOf(),
) : RecyclerView.Adapter<WindowListAdapter.MyHolder?>() {

	@SuppressLint("NotifyDataSetChanged")
	fun setData(dataNew: List<Weather>) {
		this.data = dataNew
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
		val binding = FragmentWindowListItemBinding.inflate(
			LayoutInflater.from(parent.context),
			parent,
			false
		)
		return MyHolder(binding.root)
	}

	override fun onBindViewHolder(holder: MyHolder, position: Int) {
		holder.bind(data[position])
	}

	override fun getItemCount() = data.size

	inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(weather: Weather) {
			FragmentWindowListItemBinding.bind(itemView).apply {
				tName.text = weather.city.name
				root.setOnClickListener { onItemClick.onItemClick(weather) }
			}
		}
	}
}