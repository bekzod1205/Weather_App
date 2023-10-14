package com.example.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weather.databinding.DailyWeatherItemBinding
import com.example.weather.items.Weather_day

class AdapterDay(val list: List<Weather_day>): RecyclerView.Adapter<AdapterDay.MyHolder2>(){
    class MyHolder2(binding: DailyWeatherItemBinding): RecyclerView.ViewHolder(binding.root){
        val date = binding.date
        val condition = binding.condition
        val temp = binding.day
        val mintemp = binding.night
        val image = binding.imageView
        val weather = binding.weatherday
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder2 {
        return MyHolder2(DailyWeatherItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder2, position: Int) {
        val item = list[position]
        holder.date.text = item.date
        holder.condition.text = item.context
        holder.temp.text = item.temp.toString() + "°"
        holder.mintemp.text = item.mintemp.toString() + "°"
        holder.image.load("https:" + item.icon)
    }
}