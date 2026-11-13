package com.example.navig2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.navig2.navigation.AppNavigation
import com.example.navig2.ui.theme.Navig2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navig2Theme {
                AppNavigation()
            }
        }
    }
}
//ola


