package com.example.firsttry.presentation.apps

import androidx.annotation.DrawableRes
import com.example.firsttry.R
import com.example.firsttry.presentation.apps.AppIcon

@DrawableRes
fun AppIcon.drawableRes(): Int = when (this) {
    AppIcon.CHAT -> R.drawable.ic_chat
    AppIcon.MUSIC -> R.drawable.ic_music
    AppIcon.BOOK -> R.drawable.ic_book
    AppIcon.TRAVEL -> R.drawable.ic_travel
    AppIcon.NOTES -> R.drawable.ic_notes
    AppIcon.PHOTO -> R.drawable.ic_photo
    AppIcon.DEFAULT -> R.drawable.ic_catalog
}
