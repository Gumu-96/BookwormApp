package com.gumu.bookwormapp.domain.common

enum class BookOrderByFilter(val value: String) {
    RELEVANCE("relevance"),
    NEWEST("newest")
}

enum class BookPrintTypeFilter(val value: String) {
    ALL("all"),
    BOOKS("books"),
    MAGAZINES("magazines")
}

enum class BookTypeFilter(val value: String?) {
    ALL(null),
    PARTIAL("partial"),
    FULL("full"),
    FREE_EBOOKS("free-ebooks"),
    PAID_EBOOKS("paid-ebooks"),
    EBOOKS("ebooks"),
}
