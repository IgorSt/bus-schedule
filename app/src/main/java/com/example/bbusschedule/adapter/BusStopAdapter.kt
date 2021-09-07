package com.example.bbusschedule.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bbusschedule.database.schedule.Schedule
import com.example.bbusschedule.databinding.BusStopItemBinding
import java.text.SimpleDateFormat

class BusStopAdapter(
    private val onItemClick: (Schedule) -> Unit
) : ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallBack) {

    companion object{
        private val DiffCallBack = object : DiffUtil.ItemCallback<Schedule>(){
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }

        }
    }


    class BusStopViewHolder(
        private val binding: BusStopItemBinding
        ): RecyclerView.ViewHolder(binding.root){
            @SuppressLint("SimpleDateFormat")
            fun binding(schedule: Schedule){
                binding.stopNameTextView.text = schedule.stopName
                binding.arrivalTimeTextView.text = SimpleDateFormat(
                    "h:mm a").format(schedule.arrivalTime.toLong() * 1000)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(
            BusStopItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClick(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.binding(getItem(position))
    }
}