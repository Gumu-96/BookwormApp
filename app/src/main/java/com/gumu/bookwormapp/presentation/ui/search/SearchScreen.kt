package com.gumu.bookwormapp.presentation.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.presentation.component.BookItem
import com.gumu.bookwormapp.presentation.component.ErrorItem
import com.gumu.bookwormapp.presentation.component.ErrorSurface
import com.gumu.bookwormapp.presentation.component.LoadingOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    val books = state.books?.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            SearchTopAppBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = { onEvent(SearchEvent.OnSearchQueryChange(it)) },
                onBackClick = { onEvent(SearchEvent.OnBackClick) },
                onPerformSearch = { onEvent(SearchEvent.OnPerformSearch) },
                onClearQuery = { onEvent(SearchEvent.OnClearQuery) },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            when (books?.loadState?.refresh) {
                is LoadState.Loading -> LoadingOverlay()
                is LoadState.Error -> ErrorSurface(
                    modifier = Modifier.fillMaxSize(),
                    onRetryClick = { books.retry() }
                )
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        books?.let { items ->
                            items(items = items) { bookItem ->
                                bookItem?.let { book ->
                                    BookItem(
                                        book = book,
                                        onClick = { onEvent(SearchEvent.OnBookClick(it)) }
                                    )
                                }
                            }
                            item {
                                if (items.loadState.append is LoadState.Error) {
                                    ErrorItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        onRetryClick = { items.retry() }
                                    )
                                }
                                if (items.loadState.append is LoadState.Loading) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        } ?: item { NewSearchItem() }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onPerformSearch: () -> Unit,
    onClearQuery: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    TopAppBar(
        title = {
            // TODO replace with `SearchBar` composable when available
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text(text = stringResource(id = R.string.search_field_placeholder)) },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor =  Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.arrow_back_icon_desc)
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = {
                            onClearQuery()
                            focusRequester.requestFocus()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.clear_query_icon_desc)
                            )
                        }
                    }
                },
                shape = CircleShape,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onPerformSearch()
                    }
                ),
                textStyle = TextStyle(fontSize = 16.sp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(end = 16.dp)
                    .focusRequester(focusRequester)
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun NewSearchItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search_icon_desc)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = stringResource(id = R.string.search_field_placeholder), fontWeight = FontWeight.SemiBold)
    }
}
