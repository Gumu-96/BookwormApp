package com.gumu.bookwormapp.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.gumu.bookwormapp.domain.model.BookStats
import kotlinx.serialization.json.Json

object BookwormNavType {
    val BookStatsType = object : NavType<BookStats>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): BookStats? {
            return Json.decodeFromString(
                deserializer = BookStats.serializer(),
                string = bundle.getString(key) ?: return null
            )
        }

        override fun put(bundle: Bundle, key: String, value: BookStats) {
            bundle.putString(key, Json.encodeToString(BookStats.serializer(), value))
        }

        override fun parseValue(value: String): BookStats {
            return Json.decodeFromString(BookStats.serializer(), Uri.decode(value))
        }

        override fun serializeAsValue(value: BookStats): String {
            return Uri.encode(Json.encodeToString(BookStats.serializer(), value))
        }
    }
}
