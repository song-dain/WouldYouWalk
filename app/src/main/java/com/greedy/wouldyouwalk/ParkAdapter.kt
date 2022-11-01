package com.greedy.wouldyouwalk


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.wouldyouwalk.databinding.ParkRecyclerBinding

class ParkAdapter(var parkList: List<Row>) : RecyclerView.Adapter<Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ParkRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return if (parkList == null) 0 else parkList!!.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = parkList!![position]
        holder.setItem(item)

    }

}

class Holder(val binding: ParkRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {


    lateinit var row: Row

    init {
        /* 하나의 공원 정보가 클릭 되는 상황 */
        binding.root.setOnClickListener {
            //binding.layout.visibility = View.VISIBLE
            /* 버튼 클릭시 나타나고 사라지는 클릭 이벤트 */
            binding.layout.visibility = if (binding.layout.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }

        }
    }


    fun setItem(row: Row) {
        binding.name.text = "${row?.P_PARK}"
        binding.adress.text = "${row?.P_ADDR}"
        binding.url.text = "${row?.TEMPLATE_URL}"
        binding.info.text = "${row?.USE_REFER}"
        this.row = row!!
    }
}

