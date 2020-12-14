package com.example.droid.loan.testable.ui.converter

import android.content.Context
import com.example.droid.R
import com.example.droid.loan.ui.converter.ThrowableConverter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class ThrowableConverterTest {
    @Mock
    lateinit var context: Context
    private lateinit var throwableConverter: ThrowableConverter

    @Mock
    lateinit var httpException: HttpException

    @Before
    fun onSetup() {
        throwableConverter = ThrowableConverter(
            context
        )
    }

    @Test
    fun `cast http 400 exception EXPECT string for http 400 exception`() {
        `when`(httpException.code()).thenReturn(400)
        val expected = "Пользователь с таким именем уже существует"
        `when`(context.getString(R.string.http_exception_400))
            .thenReturn("Пользователь с таким именем уже существует")

        val result = throwableConverter.cast(httpException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast http 401 exception EXPECT string for http 401 exception`() {
        `when`(httpException.code()).thenReturn(401)
        val expected = "Сначала Вам необходимо авторизоваться"
        `when`(context.getString(R.string.http_exception_401))
            .thenReturn("Сначала Вам необходимо авторизоваться")

        val result = throwableConverter.cast(httpException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast http 403 exception EXPECT string for http 403 exception`() {
        `when`(httpException.code()).thenReturn(403)
        val expected = "Данное действие недоступно"
        `when`(context.getString(R.string.http_exception_403))
            .thenReturn("Данное действие недоступно")

        val result = throwableConverter.cast(httpException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast http 404 exception EXPECT string for http 404 exception`() {
        `when`(httpException.code()).thenReturn(404)
        val expected = "Неверно введен логин или пароль"
        `when`(context.getString(R.string.http_exception_404))
            .thenReturn("Неверно введен логин или пароль")

        val result = throwableConverter.cast(httpException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast http 405 exception EXPECT string for http other client exception`() {
        `when`(httpException.code()).thenReturn(405)
        val expected = "К сожалению, произошла ошибка при взаимодействии с сервером:("
        `when`(context.getString(R.string.http_exception_other_client))
            .thenReturn("К сожалению, произошла ошибка при взаимодействии с сервером:(")

        val result = throwableConverter.cast(httpException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast http 500 exception EXPECT string for http other server exception`() {
        `when`(httpException.code()).thenReturn(500)
        val expected = "К сожалению, произошла ошибка на сервере:("
        `when`(context.getString(R.string.http_exception_other_server))
            .thenReturn("К сожалению, произошла ошибка на сервере:(")

        val result = throwableConverter.cast(httpException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast socket timeout exception EXPECT string for socket timeout exception`() {
        val expected =
            "Плохое соединение с сервером, проверьте подключение к интернету и повторите попытку"
        `when`(context.getString(R.string.socket_timeout_exception))
            .thenReturn("Плохое соединение с сервером, проверьте подключение к интернету и повторите попытку")
        val socketTimeoutException = mock(SocketTimeoutException::class.java)
        val result = throwableConverter.cast(socketTimeoutException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast unknown host exception EXPECT string for unknown host exception`() {
        val expected = "Отсутствует подключение к интернету"
        `when`(context.getString(R.string.unknown_host_exception))
            .thenReturn("Отсутствует подключение к интернету")
        val unknownHostException = mock(UnknownHostException::class.java)
        val result = throwableConverter.cast(unknownHostException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast IO exception EXPECT string for IO exception`() {
        val expected = "К сожалению, произошла локальная ошибка:("
        `when`(context.getString(R.string.input_output_exception))
            .thenReturn("К сожалению, произошла локальная ошибка:(")
        val ioException = mock(IOException::class.java)
        val result = throwableConverter.cast(ioException)

        assertEquals(expected, result)
    }

    @Test
    fun `cast unknown exception EXPECT string for other exception`() {
        val expected = "К сожалению, произошла ошибка:("
        `when`(context.getString(R.string.exception_other))
            .thenReturn("К сожалению, произошла ошибка:(")
        val newThrowable = Throwable("312321")
        val result = throwableConverter.cast(newThrowable)

        assertEquals(expected, result)
    }
}