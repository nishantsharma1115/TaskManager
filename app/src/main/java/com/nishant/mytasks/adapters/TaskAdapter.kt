package com.nishant.mytasks.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nishant.mytasks.R
import com.nishant.mytasks.databinding.SingleTaskLayoutBinding
import com.nishant.mytasks.model.Task
import com.nishant.mytasks.ui.AddTaskActivity
import java.io.Serializable

class TaskAdapter(
    val context: Context
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
            if (task.isCompleted == 0) {
                binding.taskStatus.text = "NOT DONE"
            } else {
                binding.taskStatus.text = "DONE"
            }
            if (task.isCompleted == 1) {
                binding.txtTitle.paintFlags =
                    binding.txtTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.note.paintFlags =
                    binding.note.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleTask {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleTaskLayoutBinding.inflate(inflater, parent, false)
        return SingleTask(binding)
    }

    override fun onBindViewHolder(holder: SingleTask, position: Int) {
        val current = currentList[position]
        when (current.isCompleted) {
            0 -> {
                holder.binding.taskStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.not_check_circle_icon
                    ), null, null, null
                )
            }
            1 -> {
                holder.binding.taskStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.check_circle_icon
                    ), null, null, null
                )
            }
        }
        holder.bind(current)
        holder.binding.singleTaskLayout.setOnClickListener {
            val intent = Intent(context, AddTaskActivity::class.java)
            intent.putExtra("from", "edit")
            intent.putExtra("task", current as Serializable)
            context.startActivity(intent)
        }
    }
}