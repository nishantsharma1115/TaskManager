package com.nishant.mytasks.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("setTaskCount")
fun TextView.setTaskCount(count: Int) {
    this.text = "$count Tasks"
}