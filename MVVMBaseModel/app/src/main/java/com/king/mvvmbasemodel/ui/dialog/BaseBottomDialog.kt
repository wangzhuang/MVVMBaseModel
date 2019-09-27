package com.king.mvvmbasemodel.ui.dialog

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.king.mvvmbasemodel.R

/**
 * author ：king
 * date : 2019/9/27 15:12
 * description :
 */
abstract class BaseBottomDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(cancelOutside)

        val v = inflater?.inflate(layoutRes, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }


    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun bindView()

    override fun onStart() {
        super.onStart()

        val window = dialog?.window
        val params = window!!.attributes

        params.dimAmount = dimAmount
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        if (height > 0) {
            params.height = height
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        params.gravity = Gravity.BOTTOM

        window.attributes = params
    }

    val height: Int
        get() = -1

    val dimAmount: Float
        get() = DEFAULT_DIM

    val cancelOutside: Boolean
        get() = true

    val fragmentTag: String
        get() = TAG

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, fragmentTag)
    }

    companion object {

        private val TAG = "base_bottom_dialog"

        private val DEFAULT_DIM = 0.2f
    }
}
