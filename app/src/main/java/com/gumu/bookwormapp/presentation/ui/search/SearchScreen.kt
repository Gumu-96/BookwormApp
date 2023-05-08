package com.gumu.bookwormapp.presentation.ui.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.presentation.component.BookItem
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            SearchTopAppBar()
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
                item { BookItem() }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar() {
    TopAppBar(
        title = {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = stringResource(id = R.string.search_field_placeholder)) },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor =  Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.arrow_back_icon_desc)
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_icon_desc)
                        )
                    }
                },
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(end = 16.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {}
                )
            )
        }
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchScreenPreview() {
    BookwormAppTheme {
        SearchScreen()
    }
}
