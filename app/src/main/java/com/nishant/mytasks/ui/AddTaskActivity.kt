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
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var isArchived: Boolean = false
    private var isPinned: Boolean = false
    private lateinit var time: String
    private val dataViewModel: DataViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        return Task(
            "Yes",
            day,
            binding.edtCategory.text.toString(),
            binding.edtTitle.text.toString(),
            binding.edtTask.text.toString(),
            "Not Done",
            isArchived,
            isPinned,
            time
        )
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