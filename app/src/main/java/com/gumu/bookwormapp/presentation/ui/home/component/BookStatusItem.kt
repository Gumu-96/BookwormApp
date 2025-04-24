package com.gumu.bookwormapp.presentation.ui.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import com.gumu.bookwormapp.presentation.animation.BookStatsSharedElementKey
import com.gumu.bookwormapp.presentation.animation.BookStatsSharedElementType
import com.gumu.bookwormapp.presentation.component.CustomAsyncImage
import com.gumu.bookwormapp.presentation.component.PillShapedText
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme
import com.gumu.bookwormapp.presentation.util.ReadingStatusUi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun BookStatusItem(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    bookStats: BookStats,
    onClick: (BookStats) -> Unit
) {
    with(sharedTransitionScope) {
        ElevatedCard(
            onClick = { onClick(bookStats) },
            modifier = modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = BookStatsSharedElementKey(
                            bookId = bookStats.book.id,
                            type = BookStatsSharedElementType.Bounds
                        )
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    clipInOverlayDuringTransition = OverlayClip(MaterialTheme.shapes.medium)
                )
        ) {
            Column(
                modifier = Modifier.width(160.dp)
            ) {
                Box {
                    CustomAsyncImage(
                        model = bookStats.book.thumbnail,
                        contentDescription = bookStats.book.title,
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = BookStatsSharedElementKey(
                                        bookId = bookStats.book.id,
                                        type = BookStatsSharedElementType.Image
                                    )
                                ),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .size(width = 160.dp, height = 200.dp)
                    )
                    if (bookStats.rating > 0) {
                        PillShapedText(
                            text = bookStats.rating.toString(),
                            color = MaterialTheme.colorScheme.tertiary,
                            wrapContent = true,
                            trailingIcon = {
                                Icon(imageVector = Icons.Default.Star, contentDescription = null)
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = bookStats.book.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = BookStatsSharedElementKey(
                                        bookId = bookStats.book.id,
                                        type = BookStatsSharedElementType.BookTitle
                                    )
                                ),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )
                    Text(
                        text = bookStats.book.authors?.toString()
                            ?: stringResource(id = R.string.book_unknown_data),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = BookStatsSharedElementKey(
                                        bookId = bookStats.book.id,
                                        type = BookStatsSharedElementType.Author
                                    )
                                ),
                                animatedVisibilityScope = animatedVisibilityScope
                            )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                val readingStatus = remember {
                    ReadingStatusUi.entries.first { it.value == bookStats.status }
                }
                Surface(
                    color = readingStatus.color(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = readingStatus.label),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview
@Composable
private fun BookStatusItemPreview() {
    BookwormAppTheme {
        AnimatedVisibility(true) {
            SharedTransitionLayout {
                BookStatusItem(
                    bookStats = BookStats(
                        book = Book(
                            id = "ABC123",
                            title = "Book item",
                            authors = listOf("John", "Doe"),
                            publishedDate = "May 2023",
                            description = "This is a Book item",
                            categories = listOf("Compose", "Preview"),
                            thumbnail = null
                        ),
                        status = ReadingStatus.READ,
                        rating = 3
                    ),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                    onClick = {}
                )
            }
        }
    }
}
