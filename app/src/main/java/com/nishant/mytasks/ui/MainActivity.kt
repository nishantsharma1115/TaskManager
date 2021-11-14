package com.nishant.mytasks.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nishant.mytasks.R
import com.nishant.mytasks.adapters.CategoryWithCountAdapter
import com.nishant.mytasks.adapters.EqualItemSpacingDecoration
import com.nishant.mytasks.adapters.TaskAdapter
import com.nishant.mytasks.databinding.ActivityMainBinding
import com.nishant.mytasks.room.CacheMapper
import com.nishant.mytasks.viewmodels.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dataViewModel: DataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.homePage.toolbar)

        val toggle = DuoDrawerToggle(
            this,
            binding.drawer,
            binding.homePage.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawer.setDrawerListener(toggle)
        toggle.syncState()

        binding.homePage.toolbar.setNavigationIcon(R.drawable.drawer_icon)

        binding.homePage.btnAddTask.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        val adapter = CategoryWithCountAdapter()
        binding.homePage.rvCategories.adapter = adapter
        binding.homePage.rvCategories.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.homePage.rvCategories.setHasFixedSize(true)

        lifecycleScope.launchWhenStarted {
            dataViewModel.categoryListWithCount.collect {
                if (it.isEmpty()) {
                    binding.homePage.rvCategories.visibility = View.GONE
                    binding.homePage.noTaskTextForCategory.visibility = View.VISIBLE
                } else {
                    binding.homePage.rvCategories.visibility = View.VISIBLE
                    binding.homePage.noTaskTextForCategory.visibility = View.GONE
                    adapter.submitList(it)
                }
            }
        }

        val todayTaskAdapter = TaskAdapter(this)
        val tomorrowTaskAdapter = TaskAdapter(this)
        setUpRV(todayTaskAdapter, tomorrowTaskAdapter)

        lifecycleScope.launchWhenStarted {
            dataViewModel.todayNotes.collect { list ->
                if (list.isEmpty()) {
                    binding.homePage.rvTodaysTask.visibility = View.GONE
                    binding.homePage.noTaskTextForTodayTask.visibility = View.VISIBLE
                } else {
                    changeMarginTop(binding.homePage.txtTomorrowTask)
                    todayTaskAdapter.submitList(CacheMapper().mapFromEntityList(list))
                    binding.homePage.rvTodaysTask.visibility = View.VISIBLE
                    binding.homePage.noTaskTextForTodayTask.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            dataViewModel.tomorrowNotes.collect { list ->
                if (list.isEmpty()) {
                    binding.homePage.rvTomorrowsTask.visibility = View.GONE
                    binding.homePage.noTaskTextForTomorrowTask.visibility = View.VISIBLE
                } else {
                    tomorrowTaskAdapter.submitList(CacheMapper().mapFromEntityList(list))
                    binding.homePage.rvTomorrowsTask.visibility = View.VISIBLE
                    binding.homePage.noTaskTextForTomorrowTask.visibility = View.GONE
                }
            }
        }

        binding.menuPage.archieve.setOnClickListener {
            startActivity(Intent(this, ArchieveTasksActivity::class.java))
        }
    }

    private fun setUpRV(todayTaskAdapter: TaskAdapter, tomorrowTaskAdapter: TaskAdapter) {
        binding.homePage.rvTodaysTask.adapter = todayTaskAdapter
        binding.homePage.rvTomorrowsTask.adapter = tomorrowTaskAdapter
        val todayLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val tomorrowLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.homePage.rvTodaysTask.addItemDecoration(EqualItemSpacingDecoration(15))
        binding.homePage.rvTomorrowsTask.addItemDecoration(EqualItemSpacingDecoration(15))
        binding.homePage.rvTodaysTask.layoutManager = todayLayoutManager
        binding.homePage.rvTomorrowsTask.layoutManager = tomorrowLayoutManager
        binding.homePage.rvTodaysTask.setHasFixedSize(true)
        binding.homePage.rvTomorrowsTask.setHasFixedSize(true)
    }

    private fun changeMarginTop(view: View) {
        val param = view.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, 20, 0, 0)
        view.layoutParams = param
    }
}