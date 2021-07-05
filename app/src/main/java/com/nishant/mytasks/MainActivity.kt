package com.nishant.mytasks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.nishant.mytasks.adapters.CategoryWithCountAdapter
import com.nishant.mytasks.databinding.ActivityMainBinding
import com.nishant.mytasks.ui.AddTaskActivity
import com.nishant.mytasks.ui.DataViewModel
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

//        dataViewModel.getAllCategoriesWithCount()
//        dataViewModel.getAllCategoriesStatus.observe(this, { response ->
//            when (response) {
//                is Resource.Loading -> {
//                    Log.d("List result", "Loading")
//                }
//                is Resource.Success -> {
//                    binding.homePage.rvCategories.visibility = View.VISIBLE
//                    binding.homePage.noTaskTextForCategory.visibility = View.GONE
//                    Log.d("List result", response.data.toString())
//                    if (response.data != null) {
//                        val list = response.data
//                        val adapter = CategoryWithCountAdapter()
//                        binding.homePage.rvCategories.adapter = adapter
//                        binding.homePage.rvCategories.layoutManager = LinearLayoutManager(
//                            applicationContext,
//                            LinearLayoutManager.HORIZONTAL,
//                            false
//                        )
//                        binding.homePage.rvCategories.setHasFixedSize(true)
//                        adapter.submitList(list)
//                    }
//                }
//                is Resource.Error -> {
//                    binding.homePage.rvCategories.visibility = View.GONE
//                    binding.homePage.noTaskTextForCategory.visibility = View.VISIBLE
//                }
//            }
//        })
    }
}