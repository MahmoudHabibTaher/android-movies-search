package com.bigo.movies.core.presentation

import androidx.annotation.DimenRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.verticalLayoutManager() {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.verticalDivider(@DimenRes dimenRes: Int? = null) = this.run {
    val a = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
    a.getDrawable(0)?.let { drawable ->
        val divider = dimenRes?.let {
            context.insetDrawable(drawable, it)
        } ?: drawable

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(divider)

        addItemDecoration(decoration)
    }
    a.recycle()
}