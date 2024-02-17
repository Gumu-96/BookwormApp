package com.gumu.bookwormapp.presentation.ui.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.model.Book
import com.gumu.bookwormapp.presentation.component.CustomAsyncImage
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(
    modifier: Modifier = Modifier,
    book: Book,
    onClick: (Book) -> Unit
) {
    OutlinedCard(
        onClick = { onClick(book) },
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            CustomAsyncImage(
                model = book.thumbnail,
                contentDescription = book.title,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .size(width = 110.dp, height = 150.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = book.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        id = R.string.book_author_label,
                        book.authors ?: stringResource(id = R.string.book_unknown_data)
                    ),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stringResource(
                        id = R.string.book_published_date_label,
                        book.publishedDate ?: stringResource(id = R.string.book_unknown_data)
                    ),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.categories?.toString().orEmpty(),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun BookItemPreview() {
    BookwormAppTheme {
        BookItem(
            book = Book(
                id = "ABC123",
                title = "Book item",
                authors = listOf("John", "Doe"),
                publishedDate = "May 2023",
                description = "This is a Book item",
                categories = listOf("Compose", "Preview"),
                thumbnail = null
            ),
            onClick = {}
        )
    }
}
