package com.nishant.mytasks.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EqualItemSpacingDecoration(val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = space
        outRect.right = space
    }
}