package com.gumu.bookwormapp.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.presentation.component.BookStatusItem
import com.gumu.bookwormapp.presentation.component.ErrorItem
import com.gumu.bookwormapp.presentation.component.ErrorSurface
import com.gumu.bookwormapp.presentation.component.LoadingOverlay
import com.gumu.bookwormapp.presentation.component.SuchEmptyStats

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onQueueList: LazyPagingItems<BookStats>,
    readingList: LazyPagingItems<BookStats>,
    readList: LazyPagingItems<BookStats>,
    onEvent: (HomeEvent) -> Unit
) {
    val topBarScrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            HomeTopAppBar(
                onAccountClick = { onEvent(HomeEvent.OnAccountClick) },
                scrollBehavior = topBarScrollBehaviour
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(HomeEvent.OnAddBookClick) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_book_icon_desc)
                )
            }
        },
        modifier = Modifier.nestedScroll(topBarScrollBehaviour.nestedScrollConnection)
    ) { padding ->
        if (readList.loadState.refresh is LoadState.Loading ||
                onQueueList.loadState.refresh is LoadState.Loading ||
                readList.loadState.refresh is LoadState.Loading)
            LoadingOverlay()
        else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                val onBookStatsClick: (BookStats) -> Unit = { onEvent(HomeEvent.OnBookStatsClick(it)) }
                if (readingList.itemCount > 0 && readingList.loadState.refresh !is LoadState.Error) {
                    BookStatsList(
                        label = stringResource(id = R.string.current_reading_activity_label),
                        items = readingList,
                        onBookStatsClick = onBookStatsClick
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                BookStatsList(
                    label = stringResource(id = R.string.reading_list_label),
                    items = onQueueList,
                    onBookStatsClick = onBookStatsClick,
                    onEmptyItemsAction = { onEvent(HomeEvent.OnAddBookClick) }
                )
                if (readList.itemCount > 0 && readList.loadState.refresh !is LoadState.Error) {
                    Spacer(modifier = Modifier.height(16.dp))
                    BookStatsList(
                        label = stringResource(id = R.string.read_list_label),
                        items = readList,
                        onBookStatsClick = onBookStatsClick
                    )
                }
            }
        }
    }
}

@Composable
fun BookStatsList(
    label: String,
    items: LazyPagingItems<BookStats>,
    onBookStatsClick: (BookStats) -> Unit,
    onEmptyItemsAction: (() -> Unit)? = null
) {
    Text(
        text = label,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    when (items.loadState.refresh) {
        is LoadState.Error -> {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(250.dp)
            ) {
                ErrorSurface(
                    onRetryClick = { items.retry() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        else -> {
            if (items.itemCount == 0) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(250.dp)
                ) {
                    SuchEmptyStats(
                        modifier = Modifier.fillMaxSize(),
                        onButtonClick = onEmptyItemsAction
                    )
                }
            } else {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = items) { bookStats ->
                        bookStats?.let {
                            BookStatusItem(
                                bookStats = it,
                                onClick = onBookStatsClick
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
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    onAccountClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_title_label))
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = null,
                modifier = Modifier.padding(12.dp)
            )
        },
        actions = {
            IconButton(onClick = onAccountClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(id = R.string.account_icon_desc)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}
