package com.yzq.common.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.EventBus.EventBusUtil
import com.yzq.common.EventBus.EventMsg
import com.yzq.common.mvp.presenter.CompressImgPresenter
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.mvp.view.CompressImgView
import com.yzq.common.widget.Dialog
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


/**
 * @description: fragment基类
 * @author : yzq
 * @date   : 2018/7/11
 * @time   : 9:49
 *
 */
abstract class BaseFragment : Fragment(), BaseView {

    private var loaddingDialog: MaterialDialog? = null
    private var progressDialog: MaterialDialog? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        initArgs(arguments)
        initInject()
        initPresenter()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidget()
        initData()

    }


    override fun onResume() {
        super.onResume()
        Dialog.initDialog(activity!!)
    }


    private fun initArgs(arguments: Bundle?) {


    }


    protected open fun initInject() {


    }

    protected open fun initPresenter() {


    }



    /*初始化数据*/
    protected abstract fun initData()


    /*返回布局id*/
    protected abstract fun getContentLayoutId(): Int


    /*初始化View*/
    protected fun initWidget() {

        EventBusUtil.register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(msg: EventMsg) {
    }


//    fun onBackPressed(): Boolean {
//        return false
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        EventBusUtil.unregister(this)
    }

    override fun showLoadingDialog(content: String) {

        if (loaddingDialog == null) {
            loaddingDialog = Dialog.getLoaddingDialog()
        }

        loaddingDialog!!.setContent(content)
        loaddingDialog!!.show()


    }

    override fun dismissLoadingDialog() {
        loaddingDialog!!.dismiss()
    }

    override fun showProgressDialog(title: String, content: String) {

        if (progressDialog == null) {
            progressDialog = Dialog.getProgressDialog(title, content)
        }

        progressDialog!!.show()


    }

    override fun dismissProgressDialog() {

        progressDialog!!.dismiss()
    }

    override fun changeProgress(percent: Int) {
        progressDialog!!.setProgress(percent)

    }

    override fun showErrorDialog(msg: String) {
        Dialog.showBase(content = msg)
    }

    override fun showLoadding() {


    }

    override fun showContent() {

    }

    override fun showNoData() {

    }

    override fun showNoNet() {

    }

    override fun showError(msg: String) {

    }

}