package com.greedy.wouldyouwalk

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy.wouldyouwalk.databinding.FragmentParkinfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ParkInfoFragment : Fragment() {

    lateinit var binding: FragmentParkinfoBinding

    private lateinit var parkRepository: ParkRepository
    private lateinit var parkList: List<Row>
    private lateinit var adapter: ParkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentParkinfoBinding.inflate(inflater, container, false)

        loadData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

    private fun loadData() {

        CoroutineScope(Dispatchers.Main).launch {

            withContext(Dispatchers.IO) {
                val response = ParkInfoService.getParkInfoService().parks()
                if (response.isSuccessful) {
                    parkRepository = response.body()!!

                    Log.d("response", "${parkRepository.toString()}")
                    parkList = parkRepository.SearchParkInfoService.row
                } else {
                    Log.d("Error", "${response.message()}")
                }
            }

            adapter = ParkAdapter(parkList)
            binding.recyclerView.adapter = adapter

        }

    }



}