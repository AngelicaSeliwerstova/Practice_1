package com.example.vkcourse.applist.ui

import androidx.annotation.DrawableRes
import com.example.vkcourse.applist.R
import com.example.vkcourse.applist.domain.model.CatalogAppIcon

@DrawableRes
fun CatalogAppIcon.drawableResource(): Int = when (this) {
    CatalogAppIcon.SOCIAL -> R.drawable.ic_app_social
    CatalogAppIcon.MUSIC -> R.drawable.ic_app_music
    CatalogAppIcon.VIDEO -> R.drawable.ic_app_video
    CatalogAppIcon.MAIL -> R.drawable.ic_app_mail
    CatalogAppIcon.CLOUD -> R.drawable.ic_app_cloud
    CatalogAppIcon.EDUCATION -> R.drawable.ic_app_education
    CatalogAppIcon.GAMES -> R.drawable.ic_app_games
    CatalogAppIcon.MARKET -> R.drawable.ic_app_market
    CatalogAppIcon.CALLS -> R.drawable.ic_app_calls
}
