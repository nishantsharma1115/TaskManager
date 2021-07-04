package com.nishant.mytasks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nishant.mytasks.databinding.ActivityMainBinding
import com.nishant.mytasks.ui.AddTaskActivity
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
    }
}