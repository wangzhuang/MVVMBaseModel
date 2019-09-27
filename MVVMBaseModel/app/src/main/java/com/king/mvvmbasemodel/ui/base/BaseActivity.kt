package com.king.mvvmbasemodel.ui.base

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.jaeger.library.StatusBarUtil
import com.king.mvvmbasemodel.R
import com.king.mvvmbasemodel.ui.dialog.ProgressBarDialog
import com.king.mvvmbasemodel.util.PermissionHelper
import de.greenrobot.event.EventBus
import org.jetbrains.anko.AnkoLogger

/**
 * author ：king
 * date : 2019/9/27 14:16
 * description :
 */
abstract class BaseActivity : IBase, AppCompatActivity(), AnkoLogger {
    abstract val title: String
    abstract val layoutResId: Int
    abstract fun getIntentMessageData()
    abstract override fun initView()
    abstract override fun initData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        val toolbar = findViewById(R.id.toolbar) as? Toolbar
        toolbar?.setNavigationOnClickListener {
            onBack()
        }
        val toolbar_title = findViewById(R.id.toolbar_title) as? TextView
        toolbar_title?.text = title

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        StatusBarUtil.setColor(this@BaseActivity, ContextCompat.getColor(this@BaseActivity, R.color.colorPrimary))
        getIntentMessageData()
        initView()
        initData()
    }

    open fun onBack() {
        finish()
    }

    open fun onEvent(event: Any) {

    }
    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        val configuration = Configuration()
        configuration.setToDefaults()
        resources.updateConfiguration(configuration,
            resources.displayMetrics)
        return resources
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
    var progress_bar_dialog: ProgressBarDialog? = null

    fun showProgressBarDialog(text: String) {
        progress_bar_dialog = ProgressBarDialog(text)
        progress_bar_dialog?.show(supportFragmentManager, "progress_bar_dialog")
    }

    fun hideProgressBarDialog() {
        progress_bar_dialog?.dismiss()
    }

}