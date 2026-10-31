package com.example.vkcourse.applist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.vkcourse.applist.ui.AppCatalogApp
import com.example.vkcourse.applist.ui.theme.CatalogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatalogTheme {
                AppCatalogApp()
            }
        }
    }
}
