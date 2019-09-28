package com.bigo.movies.core.presentation

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.drawable(@DrawableRes drawableResId: Int): Drawable? = ContextCompat.getDrawable(this, drawableResId)

fun Context.insetDrawable(drawable: Drawable, @DimenRes insetDimenRes: Int): Drawable? = this.run {
    val inset = resources.getDimensionPixelSize(insetDimenRes)

    InsetDrawable(drawable, inset, 0, inset, 0)
}