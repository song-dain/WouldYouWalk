package com.greedy.wouldyouwalk

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.greedy.wouldyouwalk.databinding.TimeritemrecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    var listData = mutableListOf<Timer>()
    var helper:SqliteHelper? = null

    inner class Holder(val binding: TimeritemrecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        var tTime: Timer? = null

        init {
            binding.btnDelete.setOnClickListener {
                helper?.deleteRecord(tTime!!)
                listData.remove(tTime)
                notifyDataSetChanged()

            }
        }

        fun setRecord(record: Timer) {
            binding.num.text = "${record.num}"
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            binding.date.text ="${sdf.format(System.currentTimeMillis())}"
            binding.lapTime.text = " 산책 시간 : ${record.labTime}"

            tTime = record
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = TimeritemrecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val timer = listData[position]
        holder.setRecord(timer)
    }

    override fun getItemCount(): Int {

        return listData.size
    }
}
