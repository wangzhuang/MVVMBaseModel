package com.king.mvvmbasemodel.ui.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaeger.library.StatusBarUtil
import com.king.mvvmbasemodel.R
import com.king.mvvmbasemodel.util.PermissionHelper
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.common_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * author ：king
 * date : 2019/9/27 15:24
 * description :
 */
abstract class BaseListActivity : IBase, AppCompatActivity(), AnkoLogger {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        initToolBar()
        initListViewFrame()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        StatusBarUtil.setColor(this@BaseListActivity, ContextCompat.getColor(this@BaseListActivity, R.color.colorPrimary))
        getIntentMessageData()
        initView()
        initData()
        initErrorLayout()
    }

    fun initToolBar() {
        val toolbar = findViewById(R.id.toolbar) as? Toolbar
        toolbar?.setNavigationOnClickListener {
            finish()
        }
        val toolbar_title = findViewById(R.id.toolbar_title) as? TextView
        toolbar_title?.text = title
    }

    var mRefreshLayout: SmartRefreshLayout? = null
    var mRecyclerView: RecyclerView? = null

    @SuppressLint("WrongConstant")
    fun initListViewFrame() {
        mRecyclerView = findViewById(R.id.recyler_view)
                as RecyclerView
        mRecyclerView?.apply {
            layoutManager = LinearLayoutManager(this@BaseListActivity, LinearLayoutManager.VERTICAL, false)
        }
        mRefreshLayout = findViewById(R.id.refreshLayout)
                as SmartRefreshLayout
        mRefreshLayout?.setOnRefreshListener { refreshLayout ->
            refreshLayout.layout.postDelayed({
                onRefresh()
                hideErrorLayout()
                refreshLayout.finishRefresh()
                refreshLayout.resetNoMoreData()//刷新时重制loadmore
            }, 2000)
        }
        mRefreshLayout?.setOnLoadMoreListener { refreshLayout ->
            refreshLayout.layout.postDelayed({
                onLoadMore()
                refreshLayout.finishLoadMore()
//                refreshLayout.finishLoadMoreWithNoMoreData()//将不会再次触发加载更多事件
            }, 2000)
        }
        //触发自动刷新
//        refreshLayout.autoRefresh()
    }

    //停止刷新
    fun refreshComplete() {
        mRefreshLayout?.finishRefresh()
    }

    //设置网络错误时，点击重新请求
    fun initErrorLayout() {
        error_layout.onClick {
            onRefresh()
        }
    }

    abstract val title: String
    abstract val layoutResId: Int
    abstract fun getIntentMessageData()
    abstract override fun initView()
    abstract override fun initData()
    abstract fun onRefresh()
    abstract fun onLoadMore()

    open fun onEvent(event: Any) {

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    fun showErrorLayout() {
        error_layout.visibility = View.VISIBLE
    }

    fun hideErrorLayout() {
        error_layout.visibility = View.GONE
    }

    //    getString(R.string.Do_not_open_the_lottery)
    fun resultListIsEmpty(size: Int, content: String = getString(R.string.no_data)) {
        if (size == 0) {
            showEmptylayout(content)
        } else {
            hideEmptylayout()
        }
    }


    fun showEmptylayout(content: String) {
        empty_layout.visibility = View.VISIBLE
        content_text.text = content
    }

    fun hideEmptylayout() {
        empty_layout.visibility = View.GONE
    }
    private var mAlertDialog: AlertDialog? = null
    /**
     * 请求权限
     *
     *
     * 如果权限被拒绝过，则提示用户需要权限
     */
    fun requestPermission(permission: String, rationale: String, requestCode: Int) {
        showAlertDialog(getString(R.string.internal_storage_permissions), rationale,
            DialogInterface.OnClickListener { dialog, which ->  PermissionHelper.requestPermission(this,requestCode,arrayOf(permission)); }, getString(R.string.ensure), null, getString(R.string.cancel))
    }

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     */
    protected fun showAlertDialog(title: String?, message: String?,
                                  onPositiveButtonClickListener: DialogInterface.OnClickListener?,
                                  positiveText: String,
                                  onNegativeButtonClickListener: DialogInterface.OnClickListener?,
                                  negativeText: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener)
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener)
        mAlertDialog = builder.show()
    }

}