package com.nishant.mytasks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nishant.mytasks.databinding.SingleCategoryLayoutBinding
import com.nishant.mytasks.model.TaskCount

class CategoryWithCountAdapter :
    ListAdapter<TaskCount, CategoryWithCountAdapter.SingleCategory>(DiffCall()) {

    class DiffCall : DiffUtil.ItemCallback<TaskCount>() {
        override fun areItemsTheSame(oldItem: TaskCount, newItem: TaskCount): Boolean {
            return oldItem.count == newItem.count
        }

        override fun areContentsTheSame(oldItem: TaskCount, newItem: TaskCount): Boolean {
            return oldItem == newItem
        }
    }

    class SingleCategory(private val binding: SingleCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TaskCount) {
            binding.taskCountData = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleCategory {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleCategoryLayoutBinding.inflate(inflater, parent, false)
        return SingleCategory(binding)
    }

    override fun onBindViewHolder(holder: SingleCategory, position: Int) {
        val current = currentList[position]

        holder.bind(current)
    }
}