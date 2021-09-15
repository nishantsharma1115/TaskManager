package com.nishant.mytasks.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EqualItemSpacingDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val column = position % 2
        outRect.left = column * spacing / 2
        outRect.right = spacing - (column + 1) * spacing / 2
        if (position >= 2) {
            outRect.top = spacing
        }
    }
}