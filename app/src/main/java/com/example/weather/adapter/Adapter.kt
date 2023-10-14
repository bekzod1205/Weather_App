package com.example.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather.databinding.HoursWeatherItemBinding
import com.example.weather.items.Weather_hour

class Adapter(val list:List<Weather_hour>):RecyclerView.Adapter<Adapter.MyHolder>() {
    class MyHolder(binding: HoursWeatherItemBinding):RecyclerView.ViewHolder(binding.root){
        val temp = binding.temperature
        val icon = binding.icon
        val time = binding.time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(HoursWeatherItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val weather = list[position]
        holder.temp.text = weather.temp.toString() + "°"
        holder.icon.load("https:"+weather.icon)
        holder.time.text = weather.time
    }
}