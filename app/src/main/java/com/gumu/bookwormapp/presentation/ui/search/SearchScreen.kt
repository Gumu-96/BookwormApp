package com.gumu.bookwormapp.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.presentation.component.BookItem
import com.gumu.bookwormapp.presentation.component.CustomAsyncImage
import com.gumu.bookwormapp.presentation.component.ErrorItem
import com.gumu.bookwormapp.presentation.component.ErrorSurface
import com.gumu.bookwormapp.presentation.component.FilterCategory
import com.gumu.bookwormapp.presentation.component.LoadingOverlay
import com.gumu.bookwormapp.presentation.component.SuchEmptyResults
import com.gumu.bookwormapp.presentation.util.BookOrderByFilterUi
import com.gumu.bookwormapp.presentation.util.BookPrintTypeFilterUi
import com.gumu.bookwormapp.presentation.util.BookTypeFilterUi
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit
) {
    val books = state.books?.collectAsLazyPagingItems()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    fun dismissDetails() {
        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
            if (bottomSheetState.isVisible.not()) onEvent(SearchEvent.OnHideBookDetails)
        }
    }

    LaunchedEffect(key1 = state.isAddingBook) {
        if (state.isAddingBook.not()) dismissDetails()
    }

    BottomSheetScaffold(
        sheetContent = {
            if (state.showBookDetails) {
                ModalBottomSheet(
                    onDismissRequest = { dismissDetails() },
                    sheetState = bottomSheetState
                ) {
                    state.displayBook?.let { book ->
                        BookBottomSheetContent(
                            book = book,
                            onAddClick = { onEvent(SearchEvent.OnAddBookClick(it)) },
                            isAddingBook = state.isAddingBook
                        )
                    }
                }
            }
        },
        sheetPeekHeight = 0.dp,
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            BooksSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = { onEvent(SearchEvent.OnSearchQueryChange(it)) },
                onBackClick = { onEvent(SearchEvent.OnBackClick) },
                onPerformSearch = { onEvent(SearchEvent.OnPerformSearch) },
                onClearQuery = { onEvent(SearchEvent.OnClearQuery) },
                currentFilters = state.filterOptions,
                onOrderByClick = { onEvent(SearchEvent.OnOrderByClick(it)) },
                onPrintTypeClick = { onEvent(SearchEvent.OnPrintTypeClick(it)) },
                onBookTypeClick = { onEvent(SearchEvent.OnBookTypeClick(it)) }
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
                        onBookClick = {
                            if (state.isAddingBook.not()) onEvent(SearchEvent.OnBookClick(it))
                        }
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
    onClearQuery: () -> Unit,
    currentFilters: SearchFilterOptions,
    onOrderByClick: (BookOrderByFilter) -> Unit,
    onPrintTypeClick: (BookPrintTypeFilter) -> Unit,
    onBookTypeClick: (BookTypeFilter) -> Unit
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
                    if (isSearching) {
                        isSearching = false
                    } else {
                        focusManager.clearFocus()
                        onBackClick()
                    }
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            BookOrderFilter(
                currentOption = currentFilters.bookOrder,
                onSelectOption = onOrderByClick
            )
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp))
            BookPrintTypeFilter(
                currentOption = currentFilters.bookPrintType,
                onSelectOption = onPrintTypeClick
            )
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp))
            BookTypeFilter(
                currentOption = currentFilters.bookType,
                onSelectOption = onBookTypeClick
            )
        }
    }
}

@Composable
fun BookPrintTypeFilter(
    currentOption: BookPrintTypeFilter,
    onSelectOption: (BookPrintTypeFilter) -> Unit
) {
    val context = LocalContext.current
    FilterCategory(
        title = stringResource(id = R.string.filter_print_type_title),
        options = BookPrintTypeFilterUi.values().toList(),
        optionLabel = { context.resources.getString(it.label) },
        isOptionSelected = { it.value == currentOption },
        onSelectOption = { onSelectOption(it.value) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Print, contentDescription = null) }
    )
}

@Composable
fun BookOrderFilter(
    currentOption: BookOrderByFilter,
    onSelectOption: (BookOrderByFilter) -> Unit
) {
    val context = LocalContext.current
    FilterCategory(
        title = stringResource(id = R.string.filter_order_by_title),
        options = BookOrderByFilterUi.values().toList(),
        optionLabel = { context.resources.getString(it.label) },
        isOptionSelected = { it.value == currentOption },
        onSelectOption = { onSelectOption(it.value) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Sort, contentDescription = null) }
    )
}

@Composable
fun BookTypeFilter(
    currentOption: BookTypeFilter,
    onSelectOption: (BookTypeFilter) -> Unit
) {
    val context = LocalContext.current
    FilterCategory(
        title = stringResource(id = R.string.filter_book_type_title),
        options = BookTypeFilterUi.values().toList(),
        optionLabel = { context.resources.getString(it.label) },
        isOptionSelected = { it.value == currentOption },
        onSelectOption = { onSelectOption(it.value) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.FilterList, contentDescription = null) }
    )
}

@Composable
fun BooksList(
    books: LazyPagingItems<Book>?,
    onBookClick: (Book) -> Unit
) {
    if (books?.itemCount == 0) SuchEmptyResults(modifier = Modifier.fillMaxSize())
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
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

@Composable
fun BookBottomSheetContent(
    book: Book,
    onAddClick: (Book) -> Unit,
    isAddingBook: Boolean
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            CustomAsyncImage(
                model = book.thumbnail,
                contentDescription = book.title,
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .size(width = 140.dp, height = 185.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = book.title,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .heightIn(max = 325.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(
                    id = R.string.book_author_label,
                    book.authors ?: stringResource(id = R.string.book_unknown_data)
                ),
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = stringResource(
                    id = R.string.book_published_date_label,
                    book.publishedDate ?: stringResource(id = R.string.book_unknown_data)
                ),
                fontSize = 14.sp
            )
            Text(
                text = book.categories?.toString() ?: "",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = book.description ?: stringResource(id = R.string.desc_not_available_label))
        }
        Button(
            onClick = { onAddClick(book) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            enabled = isAddingBook.not(),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (isAddingBook) CircularProgressIndicator(modifier = Modifier.size(24.dp))
            else Text(text = stringResource(id = R.string.add_to_list_button_label))
        }
    }
}
