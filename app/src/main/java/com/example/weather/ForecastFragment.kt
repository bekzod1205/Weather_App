package com.example.weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.adapter.Adapter
import com.example.weather.adapter.AdapterDay
import com.example.weather.databinding.FragmentForecastBinding
import com.example.weather.items.Weather_day
import com.example.weather.items.Weather_hour
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForecastFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val url = "https://api.weatherapi.com/v1/forecast.json?key=9bcfb053b7d247fda8c53154230810&q=Tashkent&days=7&aqi=yes&alerts=yes"
    lateinit var obj : JSONObject
    lateinit var objday : JSONObject
    val list = mutableListOf<Weather_hour>()
    val list_days = mutableListOf<Weather_day>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentForecastBinding.inflate(inflater,container,false)
        val requestQueue = Volley.newRequestQueue(requireContext())

        val request = JsonObjectRequest(url,object :Response.Listener<JSONObject>{
            override fun onResponse(response: JSONObject) {
                Log.d("TAG", "onErrorResponse: 50% ishlavotti ")
                val forecast = response.getJSONObject("forecast")

                val forecastday = forecast.getJSONArray("forecastday")
                val today = forecastday.getJSONObject(0)
                val hours = today.getJSONArray("hour")
                val todays = today.getString("date")
                var date = todays.substring(5,todays.length)
                date= date.replace('-','.')
                binding.date.text = date


                for (i in 0 until hours.length()){
                    obj = hours.getJSONObject(i)
                    list.add(create_item(obj))
                }
                binding.hoursRv.adapter = Adapter(list)

                for (i in 0 until forecastday.length()){
                    objday = forecastday.getJSONObject(i)
                    list_days.add(create_day(objday))
                }
                binding.forecastRv.adapter = AdapterDay(list_days)
            }
        },object :Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                Log.d("TAG", "onErrorResponse: $error")
            }
        })


        requestQueue.add(request)

        return binding.root
    }

    fun create_item(obj:JSONObject):Weather_hour{

        val time = obj.getString("time")
        val time2 = time.substring(11,time.length)
        val day_temp = obj.getInt("temp_c")
        val condition = obj.getJSONObject("condition")
        val image = condition.getString("icon")
        return Weather_hour(day_temp,image,time2)
    }

    fun create_day(obj:JSONObject):Weather_day{
        val date = obj.getString("date")
        val date2 = date.substring(8,10)+"."+date.substring(5,7)+"."+date.substring(0,4)
        val day = obj.getJSONObject("day")
        val day_temp = day.getInt("maxtemp_c")
        val night_temp = day.getInt("mintemp_c")
        val condition = day.getJSONObject("condition")
        val context = condition.getString("text")
        val image = condition.getString("icon")
        return Weather_day(date2,day_temp,night_temp,context,image)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForecastFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForecastFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}