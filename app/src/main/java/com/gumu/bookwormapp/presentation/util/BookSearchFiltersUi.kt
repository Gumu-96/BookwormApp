package com.gumu.bookwormapp.presentation.util

import androidx.annotation.StringRes
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.domain.common.BookOrderByFilter
import com.gumu.bookwormapp.domain.common.BookPrintTypeFilter
import com.gumu.bookwormapp.domain.common.BookTypeFilter

enum class BookOrderByFilterUi(@StringRes val label: Int, val value: BookOrderByFilter) {
    RELEVANCE(R.string.filter_order_by_relevance_label, BookOrderByFilter.RELEVANCE),
    NEWEST(R.string.filter_order_by_newest_label, BookOrderByFilter.NEWEST)
}

enum class BookPrintTypeFilterUi(@StringRes val label: Int, val value: BookPrintTypeFilter) {
    ALL(R.string.filter_print_type_all_label, BookPrintTypeFilter.ALL),
    BOOKS(R.string.filter_print_type_books_label, BookPrintTypeFilter.BOOKS),
    MAGAZINES(R.string.filter_print_type_magazines_label, BookPrintTypeFilter.MAGAZINES)
}

enum class BookTypeFilterUi(@StringRes val label: Int, val value: BookTypeFilter) {
    ALL(R.string.filter_book_type_all_label, BookTypeFilter.ALL),
    PARTIAL(R.string.filter_book_type_partial_label, BookTypeFilter.PARTIAL),
    FULL(R.string.filter_book_type_full_label, BookTypeFilter.FULL),
    FREE_EBOOKS(R.string.filter_book_type_free_ebooks_label, BookTypeFilter.FREE_EBOOKS),
    PAID_EBOOKS(R.string.filter_book_type_paid_ebooks_label, BookTypeFilter.PAID_EBOOKS),
    EBOOKS(R.string.filter_book_type_ebooks_label, BookTypeFilter.EBOOKS),
}
