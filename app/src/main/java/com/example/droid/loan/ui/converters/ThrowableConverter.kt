package com.example.droid.loan.ui.converters

import android.content.Context
import com.example.droid.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ThrowableConverter @Inject constructor(
    private val context: Context
) {
    fun cast(throwable: Throwable): String =
        when (throwable) {
            is HttpException -> when (throwable.code()) {
                400 -> context.getString(R.string.http_exception_400)
                401 -> context.getString(R.string.http_exception_401)
                403 -> context.getString(R.string.http_exception_403)
                404 -> context.getString(R.string.http_exception_404)
                in 400..417 -> context.getString(R.string.http_exception_other_client)
                in 500..505 -> context.getString(R.string.http_exception_other_server)
                else -> context.getString(R.string.http_exception_other)
            }
            is SocketTimeoutException -> context
                .getString(R.string.socket_timeout_exception)
            is UnknownHostException -> context
                .getString(R.string.unknown_host_exception)
            is IOException -> context.getString(R.string.input_output_exception)
            else -> context.getString(R.string.exception_other)
        }
}