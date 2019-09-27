package com.king.mvvmbasemodel.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.king.mvvmbasemodel.ui.base.IBase

/**
 * author ：king
 * date : 2019/9/27 14:36
 * description :
 */
abstract class BaseDialog : IBase, DialogFragment() {

    abstract val layoutResId: Int
    abstract override fun initView()
    abstract override fun initData()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(layoutResId, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }
}