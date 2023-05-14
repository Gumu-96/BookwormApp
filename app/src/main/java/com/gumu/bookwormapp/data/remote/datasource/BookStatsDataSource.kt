package com.gumu.bookwormapp.data.remote.datasource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.gumu.bookwormapp.domain.model.ReadingStatus

interface BookStatsDataSource {
    fun getBookStatsQuery(
        userId: String,
        status: ReadingStatus,
        pageSize: Int,
        startAtDocument: DocumentSnapshot?
    ): Query
}
