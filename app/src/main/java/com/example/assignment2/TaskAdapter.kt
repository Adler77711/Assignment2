package com.example.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.R
import com.example.assignment2.Task

class TaskAdapter(
    private val tasks: List<Task>,
    private val onTaskSelected: (Task, Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var filteredTasks = tasks

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.checkbox_task)
        val textView: TextView = view.findViewById(R.id.textview_task_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = filteredTasks[position]
        holder.checkBox.isChecked = task.isCompleted
        holder.textView.text = task.description
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            onTaskSelected(task, isChecked)
        }
    }

    override fun getItemCount(): Int = filteredTasks.size

    fun updateTasks(newTasks: List<Task>) {
        filteredTasks = newTasks
        notifyDataSetChanged()
    }
}
