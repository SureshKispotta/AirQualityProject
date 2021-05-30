package com.sk.qualityanalyticpage.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecorator(val horizontalSpace: Int,
                          val verticalSpace: Int ) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = horizontalSpace
        outRect.right = horizontalSpace
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = verticalSpace
        }
        outRect.bottom = verticalSpace
    }
}