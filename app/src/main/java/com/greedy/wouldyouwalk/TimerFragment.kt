package com.greedy.wouldyouwalk

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.greedy.wouldyouwalk.databinding.FragmentTimerBinding
import kotlin.concurrent.thread

class TimerFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentTimerBinding

    var total = 0
    var started = false

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d", total / 60)
            val second = String.format("%02d", total % 60)
            binding.time.text = "$minute : $second"
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val helper = SqliteHelper(mainActivity, "time", 1)

        val adapter = RecyclerAdapter()
        adapter.helper = helper
        adapter.listData.addAll(helper.selectLabTime())

        binding = FragmentTimerBinding.inflate(inflater, container, false)
        binding.recyclerview.layoutManager = LinearLayoutManager(mainActivity)
        binding.recyclerview.adapter = adapter

        binding.btnStart.setOnClickListener {

            if (!started) {
                started = true
                Toast.makeText(mainActivity, "산책 시간을 기록합니다!", Toast.LENGTH_SHORT).show()
                thread(start = true) {
                    while (started) {
                        Thread.sleep(1000)
                        if (started) {
                            total += 1
                            handler?.sendEmptyMessage(0)
                        }
                    }
                }
            }
        }

        binding.btnStop.setOnClickListener {
            if (started) {
                started = false
                Toast.makeText(mainActivity, "산책 시간을 기록하였습니다!", Toast.LENGTH_SHORT).show()
                val record = Timer(null,"${System.currentTimeMillis()}", binding.time.text.toString())
                helper.insertLabTime(record)

                var bundle = Bundle()
                bundle.putString("timeCheck", binding.time.text.toString())

                adapter.listData.clear()
                adapter.listData.addAll(helper.selectLabTime())
                adapter.notifyDataSetChanged()

                total = 0
                binding.time.text = "00 : 00"
            }

        }

        return binding.root
    }

}

