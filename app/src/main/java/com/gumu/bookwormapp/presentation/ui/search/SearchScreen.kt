package com.gumu.bookwormapp.presentation.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.presentation.component.BookItem
import com.gumu.bookwormapp.presentation.component.ErrorItem
import com.gumu.bookwormapp.presentation.component.ErrorSurface
import com.gumu.bookwormapp.presentation.component.LoadingOverlay

@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    val books = state.books?.collectAsLazyPagingItems()

    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            BooksSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = { onEvent(SearchEvent.OnSearchQueryChange(it)) },
                onBackClick = { onEvent(SearchEvent.OnBackClick) },
                onPerformSearch = { onEvent(SearchEvent.OnPerformSearch) },
                onClearQuery = { onEvent(SearchEvent.OnClearQuery) }
            )
            Box(modifier = Modifier.padding(top = 72.dp)) {
                when (books?.loadState?.refresh) {
                    is LoadState.Loading -> LoadingOverlay()
                    is LoadState.Error -> ErrorSurface(
                        modifier = Modifier.fillMaxSize(),
                        onRetryClick = { books.retry() }
                    )
                    else -> BooksList(
                        books = books,
                        onBookClick = { onEvent(SearchEvent.OnBookClick(it)) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onPerformSearch: () -> Unit,
    onClearQuery: () -> Unit
) {
    var isSearching by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .focusRequester(focusRequester),
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {
            isSearching = false
            onPerformSearch()
        },
        active = isSearching,
        onActiveChange = { isSearching = it },
        placeholder = { Text(text = stringResource(id = R.string.search_field_placeholder)) },
        leadingIcon = {
            IconButton(
                onClick = {
                    focusManager.clearFocus()
                    onBackClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.arrow_back_icon_desc)
                )
            }
        },
        trailingIcon = {
            if (searchQuery.isNotBlank()) {
                IconButton(
                    onClick = {
                        focusRequester.requestFocus()
                        onClearQuery()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.clear_query_icon_desc)
                    )
                }
            }
        }
    ) {
        // TODO -> Filtering options
    }
}

@Composable
fun BooksList(
    books: LazyPagingItems<Book>?,
    onBookClick: (Book) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        books?.let { items ->
            items(items = items) { book ->
                book?.let {
                    BookItem(
                        book = it,
                        onClick = onBookClick
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
