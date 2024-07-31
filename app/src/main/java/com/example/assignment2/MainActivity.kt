package com.example.assignment2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.assignment2.TaskListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var taskListFragment: TaskListFragment
    private lateinit var manageTasksLayout: View
    private lateinit var addTaskEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskListFragment = TaskListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, taskListFragment)
            .commit()

        manageTasksLayout = findViewById(R.id.manage_tasks_layout)
        addTaskEditText = findViewById(R.id.edit_text_add_task)
        findViewById<View>(R.id.button_add).setOnClickListener { addTask() }
        findViewById<View>(R.id.button_delete).setOnClickListener { deleteTask() }
        findViewById<View>(R.id.button_change_status).setOnClickListener { changeTaskStatus() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_all_tasks -> {
                taskListFragment.filterTasks(TaskListFragment.FilterType.ALL)
                true
            }
            R.id.menu_tasks_completed -> {
                taskListFragment.filterTasks(TaskListFragment.FilterType.COMPLETED)
                true
            }
            R.id.menu_tasks_uncompleted -> {
                taskListFragment.filterTasks(TaskListFragment.FilterType.UNCOMPLETED)
                true
            }
            R.id.menu_manage_tasks -> {
                toggleManageTasksLayout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleManageTasksLayout() {
        if (manageTasksLayout.visibility == View.GONE) {
            manageTasksLayout.visibility = View.VISIBLE
        } else {
            manageTasksLayout.visibility = View.GONE
        }
    }

    private fun addTask() {
        val description = addTaskEditText.text.toString()
        if (description.isNotBlank()) {
            taskListFragment.addTask(description)
            addTaskEditText.text.clear()
        } else {
            Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTask() {
        taskListFragment.deleteSelectedTasks()
    }

    private fun changeTaskStatus() {
        val status = findViewById<RadioButton>(R.id.radio_completed).isChecked
        taskListFragment.changeSelectedTaskStatus(status)
    }
}
