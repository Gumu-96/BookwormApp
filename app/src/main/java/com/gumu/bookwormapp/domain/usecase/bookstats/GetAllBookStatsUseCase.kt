package com.gumu.bookwormapp.domain.usecase.bookstats

import androidx.paging.PagingData
import com.gumu.bookwormapp.domain.model.BookStats
import com.gumu.bookwormapp.domain.model.ReadingStatus
import kotlinx.coroutines.flow.Flow

interface GetAllBookStatsUseCase {
    operator fun invoke(status: ReadingStatus): Flow<PagingData<BookStats>>
}
