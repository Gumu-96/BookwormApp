package com.gumu.bookwormapp.presentation.ui.bookstats

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BookStatsScreen(bookStatsId: String?) {
    Text(text = bookStatsId ?: "")
}
