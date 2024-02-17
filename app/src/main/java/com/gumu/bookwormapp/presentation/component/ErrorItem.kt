package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@Composable
fun ErrorItem(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ErrorOutline, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(id = R.string.search_error_message),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(onClick = onRetryClick) {
                Text(text = stringResource(id = R.string.retry_button_label))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Replay,
                    contentDescription = stringResource(id = R.string.retry_button_label)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorItemPreview() {
    BookwormAppTheme {
        ErrorItem(onRetryClick = { /*TODO*/ })
    }
}
