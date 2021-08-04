package com.nishant.mytasks.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.nishant.mytasks.R
import com.nishant.mytasks.databinding.ActivityAddTaskBinding
import com.nishant.mytasks.model.Task
import com.nishant.mytasks.util.Resource
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

            time = LocalTime.now().toString()

            Log.d("Here time", time)
            val task = getTask()
            if (validateInput(task)) {
                dataViewModel.insertIntoDb(task)
            }
        }

        dataViewModel.insertTaskStatus.observe(this, { response ->
            Log.d("Task Data", response.toString())
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
            Log.d("Here Archive", isArchived.toString())

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
            Log.d("Here Pinned", isPinned.toString())

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
        Log.d("Here day", day)
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
        return true
    }
}