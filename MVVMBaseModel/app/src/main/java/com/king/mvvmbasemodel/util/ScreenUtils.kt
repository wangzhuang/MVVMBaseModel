package com.king.mvvmbasemodel.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.WindowManager


/**
 * 屏幕相关辅助类
 * 创建时间： 2017/7/18.
 * 作者：WangZhuang
 * 功能描述：
 */
object ScreenUtils {

    /**
     * 获得屏幕高度

     * @param context
     * *
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.getDefaultDisplay().getMetrics(outMetrics)
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度

     * @param context
     * *
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics: DisplayMetrics = DisplayMetrics()
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度

     * @param context
     * *
     * @return
     */
    fun getStatusHeight(context: Context): Int {

        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(`object`).toString())
            statusHeight = context.getResources().getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusHeight
    }

    /**
     * 获取当前屏幕截图，包含状态栏

     * @param activity
     * *
     * @return
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp

    }

    /**
     * 获取当前屏幕截图，不包含状态栏

     * @param activity
     * *
     * @return
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top

        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        var bp: Bitmap? = null
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return bp

    }
}