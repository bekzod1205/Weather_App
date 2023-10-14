package com.example.weather

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentFactory
import coil.load
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weather.adapter.Adapter
import com.example.weather.adapter.AdapterDay
import com.example.weather.databinding.FragmentForecastBinding
import com.example.weather.databinding.FragmentMainBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val url = "https://api.weatherapi.com/v1/forecast.json?key=9bcfb053b7d247fda8c53154230810&q=Tashkent&days=7&aqi=yes&alerts=yes"

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
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        val requestQueue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(url,object : Response.Listener<JSONObject>{
            override fun onResponse(response: JSONObject) {

                val forecast = response.getJSONObject("forecast")

                val forecastday = forecast.getJSONArray("forecastday")
                val today = forecastday.getJSONObject(0)
                val todays = today.getString("date")
                val date = todays.substring(8,todays.length)

                val tempday = today.getJSONObject("day")
                val temp = tempday.getString("maxtemp_c")
                val cond = tempday.getJSONObject("condition")
                val context = cond.getString("text")

                val image = cond.getString("icon")

                binding.weathericon.load("https:"+image)
                binding.context.text = context
                binding.date.text = "Today " + date + " October"
                binding.temp.text = temp

            }
        },object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                Log.d("TAG", "onErrorResponse: $error")
            }
        })

        requestQueue.add(request)


        binding.forecast.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_activity,ForecastFragment()).commit()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}