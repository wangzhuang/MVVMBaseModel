package com.king.mvvmbasemodel.http

import com.orhanobut.logger.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * author ：king
 * date : 2019/9/27 11:30
 * description :
 */
fun <T> EnqueueCallback(): Callback<T> = object : Callback<T> {
    override fun onResponse(p0: Call<T>?, p1: Response<T>?) {
        p1.let {
            HttpCommonUtil.putMessageToActivity(p1)
        }
    }

    override fun onFailure(p0: Call<T>?, p1: Throwable?) {
        Logger.e(p0?.request()?.url()?.toString() + "\n" + p1?.message + "\n" + p0?.request()?.body())
    }
}