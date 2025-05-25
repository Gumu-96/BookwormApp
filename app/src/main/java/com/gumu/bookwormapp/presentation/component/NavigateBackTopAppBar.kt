package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigateBackTopAppBar(
    title: @Composable () -> Unit,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = title,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.arrow_back_icon_desc)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NavigateBackTopAppBarPreview() {
    BookwormAppTheme {
        NavigateBackTopAppBar(
            title = {
                Text(text = "Title")
            },
            onBackClick = {},
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
        )
    }
}
