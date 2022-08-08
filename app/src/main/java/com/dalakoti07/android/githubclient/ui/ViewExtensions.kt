package com.dalakoti07.android.githubclient.ui

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import com.dalakoti07.android.githubclient.R


fun ImageView.loadImageByUrl(
    url: String?,
    @DrawableRes errorDrawableRes: Int = R.drawable.ic_launcher_background
) {

    // To set a placeholder, use placeholder() as lambda param below. Currently we don't want to show it.
    load(url) {
        crossfade(true)
        placeholder(
            R.drawable.ic_launcher_foreground
        )
        error(errorDrawableRes)
    }
}
