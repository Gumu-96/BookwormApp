package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@Composable
fun PillShapedText(
    modifier: Modifier = Modifier,
    text: String,
    wrapContent: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = color,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    horizontal = if (wrapContent) 8.dp else 32.dp,
                    vertical = if (wrapContent) 4.dp else 8.dp
                )
        ) {
            leadingIcon?.let { it() }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.width(4.dp))
            trailingIcon?.invoke()
        }
    }
}

@Preview
@Composable
private fun PillShapedTextPreview() {
    BookwormAppTheme {
        PillShapedText(text = "Hello there")
    }
}
