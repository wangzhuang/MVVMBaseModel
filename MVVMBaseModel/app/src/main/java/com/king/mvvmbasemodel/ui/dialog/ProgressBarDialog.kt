package com.king.mvvmbasemodel.ui.dialog

import android.content.DialogInterface
import android.view.KeyEvent
import android.view.animation.LinearInterpolator
import com.github.florent37.viewanimator.ViewAnimator
import com.king.mvvmbasemodel.R
import kotlinx.android.synthetic.main.dialog_progress_bar.*

/**
 * author ：king
 * date : 2019/9/27 14:38
 * description :
 */
class ProgressBarDialog(private val content: String) : BaseDialog() {
    override val layoutResId: Int = R.layout.dialog_progress_bar
    var viewAnimator: ViewAnimator? = null
    override fun initView() {
        this.isCancelable = false
        dialog?.setOnKeyListener(object : DialogInterface.OnKeyListener {
            override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true
                }
                return false
            }
        })
        viewAnimator = ViewAnimator
            .animate(image_view)
            .rotation(360f * 2)
            .duration(1000 * 2)
            .interpolator(object : LinearInterpolator() {

            })
            .repeatCount(10000)
            .start()

        title_text.text = content
    }

    override fun initData() {
    }

    override fun onDestroy() {
        viewAnimator?.cancel()
        super.onDestroy()
    }


}