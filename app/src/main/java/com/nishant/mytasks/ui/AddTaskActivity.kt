package com.nishant.mytasks.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.nishant.mytasks.R
import com.nishant.mytasks.databinding.ActivityAddTaskBinding
import com.nishant.mytasks.model.Task
import com.nishant.mytasks.util.Resource
import com.nishant.mytasks.viewmodels.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.util.*
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var isArchived: Boolean = false
    private var isPinned: Boolean = false
    private lateinit var time: String
    private lateinit var editableTask: Task
    private var from = ""
    private val dataViewModel: DataViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        from = intent.getStringExtra("from") as String

        if (from == "edit") {
            editableTask = intent.getSerializableExtra("task") as Task
            setTaskData(editableTask)
        } else {
            setNewTaskLayout()
        }

        binding.saveTask.setOnClickListener {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            time = LocalTime.now().toString()

            val task = getTask()
            if (validateInput(task)) {
                dataViewModel.insertIntoDb(task)
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.btnMarkAsComplete.setOnClickListener {
            dataViewModel.setTaskAsComplete(editableTask.userId)
        }

        dataViewModel.setTaskAsCompleteStatus.observe(this, {
            if (it.data != null && it.data) {
                onBackPressed()
            }
        })

        dataViewModel.insertTaskStatus.observe(this, { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    onBackPressed()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.addTaskToArchieve.setOnClickListener {
            isArchived = !isArchived

            if (isArchived) {
                Toast.makeText(this, "Achieved", Toast.LENGTH_SHORT).show()
                binding.addTaskToArchieve.load(R.drawable.archieve_icon_white)
            } else {
                Toast.makeText(this, "Remove from Archieve", Toast.LENGTH_SHORT).show()
                binding.addTaskToArchieve.load(R.drawable.not_archieve)
            }
        }
        binding.addTaskToPinned.setOnClickListener {
            isPinned = !isPinned

            if (isPinned) {
                Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show()
                binding.addTaskToPinned.load(R.drawable.pinned_icon)
            } else {
                Toast.makeText(this, "Remove from pinned", Toast.LENGTH_SHORT).show()
                binding.addTaskToPinned.load(R.drawable.pin_icon)
            }
        }
    }

    private fun setNewTaskLayout() {
        binding.activityTitle.text = "Add Task"
        binding.btnMarkAsComplete.visibility = View.GONE
    }

    private fun setTaskData(editableTask: Task) {
        binding.task = editableTask
        if (editableTask.isCompleted == 1) {
            binding.saveTask.visibility = View.GONE
            binding.btnMarkAsComplete.text = "Task Completed"
        } else {
            binding.saveTask.visibility = View.VISIBLE
        }
        binding.activityTitle.text = "Edit Task"
        binding.btnMarkAsComplete.visibility = View.VISIBLE
//        if (editableTask.isArchived) {
//            binding.addTaskToArchieve.load(R.drawable.archieve_icon_white)
//        } else {
//            binding.addTaskToArchieve.load(R.drawable.not_archieve)
//        }
//        if (editableTask.isPinned) {
//            binding.addTaskToPinned.load(R.drawable.pinned_icon)
//        } else {
//            binding.addTaskToPinned.load(R.drawable.pin_icon)
//        }
        when (editableTask.day) {
            "Today" -> {
                binding.taskDayLayout.check(R.id.todayTaskButton)
            }
            "Tomorrow" -> {
                binding.taskDayLayout.check(R.id.tomorrowTaskButton)
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.saveTask.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.saveTask.visibility = View.VISIBLE
    }

    private fun getTask(): Task {
        val button = findViewById<RadioButton>(binding.taskDayLayout.checkedRadioButtonId)
        val day = button.text.toString()

        if (from == "edit") {
            editableTask.day = day
            editableTask.category = binding.edtCategory.text.toString()
            editableTask.title = binding.edtTitle.text.toString()
            editableTask.task = binding.edtTask.text.toString()
            editableTask.isArchived = isArchived
            editableTask.isPinned = isPinned
            editableTask.time = time

            return editableTask
        } else {
            return Task(
                UUID.randomUUID().toString(),
                day,
                binding.edtCategory.text.toString(),
                binding.edtTitle.text.toString(),
                binding.edtTask.text.toString(),
                0,
                isArchived,
                isPinned,
                time
            )
        }
    }

    private fun validateInput(task: Task): Boolean {
        if (task.category.isEmpty()) {
            Snackbar.make(binding.edtCategory, "Please enter Category", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        if (task.title.isEmpty()) {
            Snackbar.make(binding.edtCategory, "Please enter title", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        if (task.task.isEmpty()) {
            Snackbar.make(binding.edtCategory, "Please enter task", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        if (task.task.isEmpty() && task.title.isEmpty()) {
            Snackbar.make(binding.edtCategory, "Please enter title or task", Snackbar.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }
}