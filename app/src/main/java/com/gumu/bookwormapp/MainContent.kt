package com.gumu.bookwormapp

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.bookwormapp.presentation.component.InfoBanner
import com.gumu.bookwormapp.presentation.navigation.BookwormNavigation
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(isConnected: Boolean) {
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !isConnected,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                InfoBanner(
                    text = stringResource(R.string.internet_not_available_msg),
                    contentColor = MaterialTheme.colorScheme.onError,
                    color = MaterialTheme.colorScheme.error,
                    icon = Icons.Default.WifiOff,
                    modifier = Modifier.fillMaxWidth().safeDrawingPadding()
                )
            }
        }
    ) {
        BookwormNavigation()
    }
}

@Preview
@Composable
private fun MainContentPreview() {
    BookwormAppTheme {
        MainContent(false)
    }
}
