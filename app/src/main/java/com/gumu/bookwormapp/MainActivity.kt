package com.gumu.bookwormapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gumu.bookwormapp.presentation.navigation.BookwormNavigation
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            BookwormAppTheme {
                BookwormNavigation()
            }
        }
    }
}
