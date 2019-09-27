package com.king.mvvmbasemodel.http.apiconfig

/**
 * author ：king
 * date : 2019/9/27 10:31
 * package：com.king.mvvmbasemodel.http.apiconfig
 * description :
 */
object HttpConfig {
    val IS_TESTING_SERVER = true
    val IS_UAT = false
    val TEST_SERVER = "http://baike.baidu.com/api/openapi/"
    val ONLINE_SERVER = "http://baidu.com"
    val SERVER_URL = if (IS_TESTING_SERVER)
        TEST_SERVER
    else
        ONLINE_SERVER

    fun getServiceHost(): String {
        return SERVER_URL
    }

}