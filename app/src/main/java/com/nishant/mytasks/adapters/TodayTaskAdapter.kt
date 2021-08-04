package com.nishant.mytasks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nishant.mytasks.databinding.SingleTaskLayoutBinding
import com.nishant.mytasks.model.Task

class TodayTaskAdapter : ListAdapter<Task, TodayTaskAdapter.SingleTask>(DiffCall()) {

    class DiffCall : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    class SingleTask(private val binding: SingleTaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.todayTask = task
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleTask {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleTaskLayoutBinding.inflate(inflater, parent, false)
        return SingleTask(binding)
    }

    override fun onBindViewHolder(holder: SingleTask, position: Int) {
        val current = currentList[position]
        holder.bind(current)
    }
}