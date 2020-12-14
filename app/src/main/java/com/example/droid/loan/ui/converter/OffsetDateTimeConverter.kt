package com.example.droid.loan.ui.converter

import android.content.Context
import com.example.droid.R
import org.threeten.bp.OffsetDateTime
import javax.inject.Inject

class OffsetDateTimeConverter @Inject constructor(
    private val context: Context
) {
    fun fromOffsetDateTimeString(offsetDateTimeString: String): String =
        try {
            val offsetDateTime = OffsetDateTime.parse(offsetDateTimeString)
            val localDateTime = offsetDateTime.toLocalDateTime()
            val newDate = localDateTime.toLocalDate()
            val newTime = localDateTime.toLocalTime()
            String.format(
                context.getString(R.string.time_with_2_args_text_view),
                newDate,
                newTime
            )
        } catch (e: Exception) {
            String.format(
                context.getString(R.string.time_with_1_arg_text_view),
                offsetDateTimeString
            )
        }
}