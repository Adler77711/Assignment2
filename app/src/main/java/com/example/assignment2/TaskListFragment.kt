package com.example.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.*
import com.example.assignment2.R
import com.example.assignment2.TaskAdapter
import com.example.assignment2.Task

class TaskListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()
    private val selectedTasks = mutableListOf<Task>()

    enum class FilterType {
        ALL, COMPLETED, UNCOMPLETED
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        taskAdapter = TaskAdapter(taskList) { task: Task, isChecked: Boolean ->
            Toast.makeText(context, "Task selected: ${task.description}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = taskAdapter
        return view
    }


    fun filterTasks(filterType: FilterType) {
        val filteredList = when (filterType) {
            FilterType.ALL -> taskList
            FilterType.COMPLETED -> taskList.filter { it.isCompleted }
            FilterType.UNCOMPLETED -> taskList.filter { !it.isCompleted }
        }
        taskAdapter.updateTasks(filteredList)
    }

    fun addTask(description: String) {
        val newTask = Task(id = taskList.size + 1, description = description, isCompleted = false)
        taskList.add(newTask)
        taskAdapter.updateTasks(taskList)
    }

    fun deleteSelectedTasks() {
        taskList.removeAll(selectedTasks)
        selectedTasks.clear()
        taskAdapter.updateTasks(taskList)
    }

    fun changeSelectedTaskStatus(isCompleted: Boolean) {
        for (task in selectedTasks) {
            task.isCompleted = isCompleted
        }
        selectedTasks.clear()
        taskAdapter.updateTasks(taskList)
    }
    private fun onTaskSelected(task: Task, isSelected: Boolean) {
        if (isSelected) {
            selectedTasks.add(task)
        } else {
            selectedTasks.remove(task)
        }
    }
}
