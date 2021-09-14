package com.nishant.mytasks.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nishant.mytasks.R
import com.nishant.mytasks.databinding.SingleTaskLayoutBinding
import com.nishant.mytasks.model.Task

class TaskAdapter(
    public val context: Context
) : ListAdapter<Task, TaskAdapter.SingleTask>(DiffCall()) {

    class DiffCall : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }

    class SingleTask(val binding: SingleTaskLayoutBinding) :
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
        if (current.taskStatus == "Done") {
            holder.binding.taskStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.check_circle_icon
                ), null, null, null
            )
        } else {
            holder.binding.taskStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.not_check_circle_icon
                ), null, null, null
            )
        }
        holder.bind(current)
    }
}