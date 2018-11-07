package com.yzq.common.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.yzq.common.eventBus.EventBusUtil
import com.yzq.common.eventBus.EventMsg
import com.yzq.common.mvp.presenter.CompressImgPresenter
import com.yzq.common.mvp.view.BaseView
import com.yzq.common.mvp.view.CompressImgView
import com.yzq.common.widget.Dialog
import com.yzq.common.widget.StateView
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
abstract class BaseFragment : Fragment(), BaseView, CompressImgView {

    private var loaddingDialog: MaterialDialog? = null
    private var progressDialog: MaterialDialog? = null

    private var stateView: StateView? = null
    private var contentLayout: View? = null
    @Inject
    lateinit var compressImgPresenter: CompressImgPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        initArgs(arguments!!)
        initInject()
        initPresenter()

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(getContentLayoutId(), container, false)

        //Dialog.initDialog(activity!!)
        EventBusUtil.register(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidget()
        initData()

    }


    protected  open fun initArgs(arguments: Bundle) {


    }


    protected open fun initInject() {


    }

    protected open fun initPresenter() {


    }


    /*初始化数据*/
    protected  fun initData(){

    }


    /*返回布局id*/
    protected abstract fun getContentLayoutId(): Int


    /*初始化View*/
    protected fun initWidget() {


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(msg: EventMsg) {
    }


//    fun onBackPressed(): Boolean {
//        return false
//    }


    protected fun initCompressImgPresenter() {
        compressImgPresenter.initPresenter(this, this)
    }


    override fun compressImgSuccess(path: String) {
        LogUtils.i("压缩后图片路径：" + path)
    }

    protected fun preViewImg(name: String, path: String) {

        val intent = Intent(activity, ImgPreviewActivity::class.java)
        intent.putExtra(ImgPreviewActivity.IMG_NAME, name)
        intent.putExtra(ImgPreviewActivity.IMG_PATH, path)

        startActivity(intent)

    }

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
        stateView?.showLoading()
        contentLayout?.visibility = View.GONE

    }

    override fun showContent() {
        stateView?.hide()
        contentLayout?.visibility = View.VISIBLE

    }

    override fun showNoData() {
        stateView?.showNoData()
        contentLayout?.visibility = View.GONE

    }

    override fun showNoNet() {
        stateView?.showNoNet()
        contentLayout?.visibility = View.GONE

    }

    override fun showError(msg: String) {
        stateView?.showError(msg)
        contentLayout?.visibility = View.GONE
    }


    protected fun initStateView(stateView: StateView, contentLayout: View) {
        this.stateView = stateView
        this.contentLayout = contentLayout
    }

}