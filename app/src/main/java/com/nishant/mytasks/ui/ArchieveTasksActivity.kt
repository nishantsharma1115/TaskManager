package com.nishant.mytasks.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nishant.mytasks.R
import com.nishant.mytasks.adapters.EqualItemSpacingDecoration
import com.nishant.mytasks.adapters.TaskAdapter
import com.nishant.mytasks.databinding.ActivityArchieveTasksBinding
import com.nishant.mytasks.room.CacheMapper
import com.nishant.mytasks.viewmodels.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ArchieveTasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArchieveTasksBinding
    private val dataViewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_archieve_tasks)

        val archieveTaskAdapter = TaskAdapter()
        binding.rvArchieveTask.adapter = archieveTaskAdapter
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvArchieveTask.addItemDecoration(EqualItemSpacingDecoration(15))
        binding.rvArchieveTask.layoutManager = layoutManager
        binding.rvArchieveTask.setHasFixedSize(true)

        lifecycleScope.launchWhenStarted {
            dataViewModel.archieveTasks.collect { list ->
                if (list.isEmpty()) {
                    binding.rvArchieveTask.visibility = View.GONE
                    binding.txtNoArchieveTasks.visibility = View.VISIBLE
                } else {
                    archieveTaskAdapter.submitList(CacheMapper().mapFromEntityList(list))
                    binding.rvArchieveTask.visibility = View.VISIBLE
                    binding.txtNoArchieveTasks.visibility = View.GONE
                }
            }
        }
    }
}