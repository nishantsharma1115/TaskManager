package com.nishant.mytasks

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle

class MainActivity : AppCompatActivity() {

    private val drawer: DuoDrawerLayout by lazy {
        findViewById(R.id.drawer)
    }
    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar)
    }
    private val curve: ImageView by lazy {
        findViewById(R.id.consistencyGraph)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toggle = DuoDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.setHomeAsUpIndicator(R.drawable.drawer_icon)
        drawer.setDrawerListener(toggle)
        toggle.syncState()
    }
}