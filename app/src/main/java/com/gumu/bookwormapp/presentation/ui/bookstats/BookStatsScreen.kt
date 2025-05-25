package com.gumu.bookwormapp.presentation.ui.bookstats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.presentation.animation.BookStatsSharedElementKey
import com.gumu.bookwormapp.presentation.animation.BookStatsSharedElementType
import com.gumu.bookwormapp.presentation.component.CustomAsyncImage
import com.gumu.bookwormapp.presentation.component.CustomOutlinedTextField
import com.gumu.bookwormapp.presentation.component.LoadingOverlay
import com.gumu.bookwormapp.presentation.component.NavigateBackTopAppBar
import com.gumu.bookwormapp.presentation.component.SuchEmptyStats
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme
import com.gumu.bookwormapp.presentation.util.ReadingStatusUi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun BookStatsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    state: BookStatsState,
    onIntent: (BookStatsIntent) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            NavigateBackTopAppBar(
                title = { Text(text = stringResource(id = R.string.book_stats_screen_title_label)) },
                onBackClick = { onIntent(BookStatsIntent.OnBackClick) },
                actions = {
                    state.book?.let {
                        IconButton(onClick = { onIntent(BookStatsIntent.OnDeleteClick) }) {
                            Icon(
                                imageVector = Icons.Default.DeleteForever,
                                contentDescription = stringResource(id = R.string.delete_icon_desc),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            LoadingOverlay()
        } else {
            state.book?.let { book ->
                if (state.showDeleteDialog) {
                    ConfirmDeleteDialog(
                        onConfirm = { onIntent(BookStatsIntent.OnConfirmDelete) },
                        onDismiss = { onIntent(BookStatsIntent.OnDismissDialog) }
                    )
                }
                if (state.showLeaveDialog) {
                    ConfirmLeaveDialog(
                        onConfirm = { onIntent(BookStatsIntent.OnConfirmLeave) },
                        onDismiss = { onIntent(BookStatsIntent.OnDismissDialog) }
                    )
                }
                StatsContent(
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    book = book,
                    thoughts = state.thoughts,
                    rating = state.rating,
                    status = state.status,
                    hasChanges = state.hasChanges,
                    isSavingChanges = state.savingChanges,
                    onThoughtsChange = { onIntent(BookStatsIntent.OnThoughtsChange(it)) },
                    onStarClick = { onIntent(BookStatsIntent.OnSetRating(it)) },
                    onStatusChange = { onIntent(BookStatsIntent.OnStatusChange(it)) },
                    onSaveChanges = { onIntent(BookStatsIntent.OnSaveChangesClick) },
                    modifier = Modifier.padding(padding)
                )
            } ?: SuchEmptyStats(modifier = Modifier.fillMaxSize())
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun StatsContent(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    book: Book,
    thoughts: String?,
    rating: Int,
    status: ReadingStatus,
    hasChanges: Boolean,
    isSavingChanges: Boolean,
    onThoughtsChange: (String) -> Unit,
    onStarClick: (Int) -> Unit,
    onStatusChange: (ReadingStatus) -> Unit,
    onSaveChanges: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
            .verticalScroll(rememberScrollState())
    ) {
        BookSection(
            book = book,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        RatingSection(
            rating = rating,
            onStarClick = onStarClick,
            thoughts = thoughts.orEmpty(),
            onThoughtsChange = onThoughtsChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        ActionsSection(
            status = status,
            onStatusChange = onStatusChange
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onSaveChanges,
            enabled = hasChanges and isSavingChanges.not(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (isSavingChanges) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text(text = stringResource(id = R.string.save_changes_button_label))
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BookSection(
    book: Book,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        with(sharedTransitionScope) {
            CustomAsyncImage(
                model = book.thumbnail,
                contentDescription = book.title,
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            key = BookStatsSharedElementKey(
                                bookId = book.id,
                                type = BookStatsSharedElementType.Image
                            )
                        ),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .size(width = 140.dp, height = 185.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = book.title,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = BookStatsSharedElementKey(
                                    bookId = book.id,
                                    type = BookStatsSharedElementType.BookTitle
                                )
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        id = R.string.book_author_label,
                        book.authors ?: stringResource(id = R.string.book_unknown_data)
                    ),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = BookStatsSharedElementKey(
                                    bookId = book.id,
                                    type = BookStatsSharedElementType.Author
                                )
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }
        }
    }
}

@Composable
fun RatingSection(
    rating: Int,
    onStarClick: (Int) -> Unit,
    thoughts: String,
    onThoughtsChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.rating_label),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.width(16.dp))
            for (i in 1..5) {
                Icon(
                    imageVector = if (rating >= i) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable(onClick = { onStarClick(i) })
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = thoughts,
            onValueChange = onThoughtsChange,
            maxLines = 4,
            label = { Text(text = stringResource(id = R.string.thoughts_field_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        )
    }
}

@Composable
fun ActionsSection(
    status: ReadingStatus,
    onStatusChange: (ReadingStatus) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.current_status_label))
                append(" ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(stringResource(id = ReadingStatusUi.entries.first { it.value == status }.label))
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (status != ReadingStatus.ON_QUEUE) {
                FilledTonalButton(
                    onClick = { onStatusChange(ReadingStatus.ON_QUEUE) },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Default.Queue, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = stringResource(id = R.string.back_to_queue_button_label))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (status != ReadingStatus.READING) {
                FilledTonalButton(
                    onClick = { onStatusChange(ReadingStatus.READING) },
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(imageVector = Icons.Default.Start, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = stringResource(id = R.string.start_reading_button_label))
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            if (status != ReadingStatus.READ) {
                FilledTonalButton(onClick = { onStatusChange(ReadingStatus.READ) }) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = stringResource(id = R.string.mark_as_read_button_label))
                }
            }
        }
    }
}

@Composable
fun ConfirmDeleteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.confirm_delete_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.confirm_delete_dialog_desc)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(id = R.string.delete_dialog_button_label),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel_dialog_button_label))
            }
        }
    )
}

@Composable
fun ConfirmLeaveDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.unsaved_changes_dialog_title)) },
        text = { Text(text = stringResource(id = R.string.unsaved_changes_dialog_desc)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.leave_dialog_button_label))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel_dialog_button_label))
            }
        }
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun BookStatsScreenPreview() {
    BookwormAppTheme {
        AnimatedVisibility(true) {
            SharedTransitionLayout {
                BookStatsScreen(
                    state = BookStatsState(
                        book = Book(
                            id = "ABC123",
                            title = "Book item",
                            authors = listOf("John", "Doe"),
                            publishedDate = "May 2023",
                            description = "This is a Book item",
                            categories = listOf("Compose", "Preview"),
                            thumbnail = null
                        )
                    ),
                    onIntent = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility
                )
            }
        }
    }
}
