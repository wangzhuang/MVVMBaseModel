package com.king.mvvmbasemodel.http

import com.model.basemodel.http.demoApi.DemoAPI

/**
 * author ：king
 * date : 2019/9/27 14:06
 * description :
 */
object ApiFactory {
    private var demoAPI: DemoAPI? = null

    fun getDemoAPI(): DemoAPI? {
        if (demoAPI == null) {
            demoAPI = RetrofitClient.retrofit.create<DemoAPI>(DemoAPI::class.java)
        }
        return demoAPI
    }
}