package com.king.mvvmbasemodel.http

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

/**
 * author ：king
 * date : 2019/9/27 11:23
 * description :
 */
class StringConverter : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        Log.d("type: ", type!!.toString())

        if (type === String::class.java) {
            return StringResponseBodyConverter.INSTANCE
        }

        return null
    }

    internal class StringResponseBodyConverter : Converter<ResponseBody, String> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): String {
            return value.string()
        }

        companion object {
            val INSTANCE = StringResponseBodyConverter()
        }
    }

    companion object {

        fun create(): Converter.Factory {
            return StringConverter()
        }
    }
}
