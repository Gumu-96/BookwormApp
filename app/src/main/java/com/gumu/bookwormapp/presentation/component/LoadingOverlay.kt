package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@Composable
fun LoadingOverlay() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun LoadingOverlayPreview() {
    BookwormAppTheme {
        LoadingOverlay()
    }
}
