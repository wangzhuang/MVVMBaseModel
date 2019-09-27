package com.model.basemodel.http.demoApi

import com.king.mvvmbasemodel.http.ApiFactory
import com.king.mvvmbasemodel.http.EnqueueCallback


/**
 * BaseModel
 * Created by WZ.
 */
fun userInfo() {
    ApiFactory.getDemoAPI()
            ?.userInfo()
            ?.enqueue(EnqueueCallback<String>())
}