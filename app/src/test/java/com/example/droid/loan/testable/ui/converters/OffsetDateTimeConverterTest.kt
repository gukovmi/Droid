package com.example.droid.loan.testable.ui.converters

import android.content.Context
import com.example.droid.R
import com.example.droid.loan.ui.converters.OffsetDateTimeConverter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OffsetDateTimeConverterTest {
    @Mock
    lateinit var context: Context
    private lateinit var offsetDateTimeConverter: OffsetDateTimeConverter

    @Before
    fun onSetup() {
        offsetDateTimeConverter = OffsetDateTimeConverter(
            context
        )
    }

    @Test
    fun `from correct offsetDataTime string EXPECT string with 2 args`() {
        val offsetDataTimeString = "2020-11-30T18:37:15.922+00:00"
        val expected = "Дата подачи заявки: 2020-11-30 \nВремя: 18:37:15.922"
        `when`(context.getString(R.string.time_with_2_args_text_view))
            .thenReturn("Дата подачи заявки: %1\$s \nВремя: %2\$s")

        val result = offsetDateTimeConverter.fromOffsetDateTimeString(offsetDataTimeString)

        assertEquals(expected, result)
    }

    @Test
    fun `from incorrect offsetDataTime string EXPECT string with 1 arg`() {
        val offsetDataTimeString = "2020-11-30 18:37:15.922"
        val expected = "Время подачи заявки: 2020-11-30 18:37:15.922"
        `when`(context.getString(R.string.time_with_1_arg_text_view))
            .thenReturn("Время подачи заявки: %1\$s")

        val result = offsetDateTimeConverter.fromOffsetDateTimeString(offsetDataTimeString)

        assertEquals(expected, result)
    }
}