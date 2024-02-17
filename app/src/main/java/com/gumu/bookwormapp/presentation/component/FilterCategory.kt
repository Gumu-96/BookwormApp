package com.gumu.bookwormapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun <Option> FilterCategory(
    modifier: Modifier = Modifier,
    title: String,
    options: List<Option>,
    optionLabel: (Option) -> String,
    isOptionSelected: (Option) -> Boolean,
    onSelectOption: (Option) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            leadingIcon?.let {
                it()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text = title, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach {
                FilterChip(
                    selected = isOptionSelected(it),
                    onClick = { onSelectOption(it) },
                    label = { Text(text = optionLabel(it)) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun FilterCategoryPreview() {
    BookwormAppTheme {
        Surface {
            FilterCategory(
                title = "Compose components",
                options = listOf("Text", "Icon", "Button", "TextField", "Switch", "Scaffold"),
                optionLabel = { it },
                isOptionSelected = { it == "Icon" },
                onSelectOption = {}
            )
        }
    }
}
