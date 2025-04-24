package com.gumu.bookwormapp.domain.model

import com.gumu.bookwormapp.domain.serialization.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class BookStats(
    val book: Book,
    val status: ReadingStatus = ReadingStatus.ON_QUEUE,
    val rating: Int = 0,
    val thoughts: String? = null,
    val id: String? = null,
    @Serializable(with = DateSerializer::class) val startedReading: Date? = null,
    @Serializable(with = DateSerializer::class) val finishedReading: Date? = null,
)
