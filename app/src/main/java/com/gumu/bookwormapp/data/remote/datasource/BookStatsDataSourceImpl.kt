package com.gumu.bookwormapp.data.remote.datasource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gumu.bookwormapp.data.remote.RemoteConstants
import com.gumu.bookwormapp.domain.model.ReadingStatus
import javax.inject.Inject

class BookStatsDataSourceImpl @Inject constructor() : BookStatsDataSource {
    val firestore = Firebase.firestore

    override fun getBookStatsQuery(
        userId: String,
        status: ReadingStatus,
        pageSize: Int,
        startAtDocument: DocumentSnapshot?
    ): Query {
        return firestore
            .collection(RemoteConstants.BOOK_STATS_COLLECTION)
            .whereEqualTo(RemoteConstants.USER_ID_FIELD, userId)
            .whereEqualTo(RemoteConstants.BOOK_STATUS_FIELD, status)
            .orderBy(RemoteConstants.BOOK_ID_FIELD)
            .run { startAtDocument?.let { startAfter(it) } ?: this }
            .limit(pageSize.toLong())
    }
}
