package com.gumu.bookwormapp.presentation.ui.bookstats

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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
import com.gumu.bookwormapp.presentation.component.CustomOutlinedTextField
import com.gumu.bookwormapp.presentation.component.NavigateBackTopAppBar
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookStatsScreen(bookStatsId: String?) {
    Scaffold(
        topBar = {
            NavigateBackTopAppBar(
                title = { Text(text = stringResource(id = R.string.book_stats_screen_title_label)) },
                onBackClick = { /*TODO*/ },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = stringResource(id = R.string.delete_icon_desc),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            BookSection()
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            RatingSection()
            Spacer(modifier = Modifier.height(16.dp))
            ActionsSection()
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = stringResource(id = R.string.save_changes_button_label))
            }
        }
    }
}

@Composable
fun BookSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bookworm),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .size(width = 130.dp, height = 185.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vel rhoncus erat. Vestibulum feugiat nibh eu nibh lobortis, quis tempor diam convallis.",
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "[Author 1, Author 2, Author 3, Author 4]",
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun RatingSection() {
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
                    imageVector = if (4 >= i) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable { }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomOutlinedTextField(
            value = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam vel rhoncus erat. Vestibulum feugiat nibh eu nibh lobortis, quis tempor diam convallis.",
            onValueChange = {},
            maxLines = 4,
            label = { Text(text = stringResource(id = R.string.thoughts_field_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
        )
    }
}

@Composable
fun ActionsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = buildAnnotatedString {
            append(stringResource(id = R.string.current_status_label))
            append(" ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                append("On queue")
            }
        })
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = { /*TODO*/ },
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
            FilledTonalButton(onClick = { /*TODO*/ },) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = stringResource(id = R.string.mark_as_read_button_label))
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BookStatsScreenPreview() {
    BookwormAppTheme {
        BookStatsScreen(bookStatsId = null)
    }
}
