package com.nishant.mytasks.ui

import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nishant.mytasks.R
import com.nishant.mytasks.databinding.ActivityAddTaskBinding
import com.nishant.mytasks.model.Task
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.hours
import kotlin.time.minutes
import kotlin.time.seconds

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var isArchived: Boolean = false
    private var isPinned: Boolean = false
    private lateinit var time: String
    private val dataViewModel: DataViewModel by viewModels()

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveTask.setOnClickListener {
            val dateNow = Calendar.getInstance().time
            time = dateNow.time.hours.toString() +
                    ":" + dateNow.time.minutes.toString() +
                    ":" + dateNow.time.seconds.toString()

            Log.d("Here time", time.toString())
            val task = getTask()
            if (validateInput(task)) {
                dataViewModel.insertIntoDb(task)
            }
        }

        dataViewModel.insertTaskStatus.observe(this, {
            Log.d("Task Data", it.data.toString())
        })

        binding.addTaskToArchieve.setOnClickListener {
            isArchived = !isArchived
            Log.d("Here Archive", isArchived.toString())

            if (isArchived) {
                binding.addTaskToArchieve.setImageResource(R.drawable.archieve_icon)
            } else {
                binding.addTaskToArchieve.setImageResource(R.drawable.not_archieve)
            }
        }
        binding.addTaskToPinned.setOnClickListener {
            isPinned = !isPinned
            Log.d("Here Pinned", isPinned.toString())

            if (isPinned) {
                binding.addTaskToArchieve.setImageResource(R.drawable.pinned_icon)
            } else {
                binding.addTaskToArchieve.setImageResource(R.drawable.pin_icon)
            }
        }
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
            isArchived,
            isPinned,
            time
        )
    }

    private fun validateInput(task: Task): Boolean {
        return true
    }
}