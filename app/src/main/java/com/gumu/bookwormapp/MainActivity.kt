package com.gumu.bookwormapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme
import com.gumu.bookwormapp.presentation.ui.signin.SignInScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookwormAppTheme {
                SignInScreen()
            }
        }
    }
}
