package com.king.mvvmbasemodel.http

import de.greenrobot.event.EventBus
import retrofit2.Response

/**
 * author ：king
 * date : 2019/9/27 11:29
 * description :
 */
object HttpCommonUtil {
    fun <T> putMessageToActivity(p1: Response<T>?) {
        if (p1?.code() == 200) {
            when (p1.headers()?.get("error-code").isNullOrEmpty()) {
                true -> {
                    EventBus.getDefault().post(p1.body() ?: "")
                }
                false -> {
                    return
                }
            }
        } else {
            //错误处理
        }

    }
}