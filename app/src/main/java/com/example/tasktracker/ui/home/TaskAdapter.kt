package com.example.tasktracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasktracker.databinding.TaskViewBinding
import com.example.tasktracker.network.getTaskModel.Data


class TaskAdapter : ListAdapter<Data, TaskAdapter.TaskViewHolder>(DiffCallback) {
    class TaskViewHolder(var binding: TaskViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.tvDate.text = data.date
            binding.tvSpentTime.text = data.spentTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = TaskViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val data = getItem(position)
        return holder.bind((data))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return (oldItem.date == newItem.date) && (oldItem.spentTime == newItem.spentTime)
        }

    }
}