package com.gumu.bookwormapp.presentation.util

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.model.ReadingStatus

enum class ReadingStatusUi(@StringRes val label: Int, val color: @Composable () -> Color, val value: ReadingStatus) {
    ON_QUEUE(R.string.status_on_queue_label, { MaterialTheme.colorScheme.outline }, ReadingStatus.ON_QUEUE),
    READING(R.string.status_reading_label, { MaterialTheme.colorScheme.primaryContainer }, ReadingStatus.READING),
    READ(R.string.status_read_label, { MaterialTheme.colorScheme.secondaryContainer }, ReadingStatus.READ)
}
