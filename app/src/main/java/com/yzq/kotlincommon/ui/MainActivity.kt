package com.yzq.kotlincommon.ui

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.ui.DefaultImagePicker
import com.yzq.common.net.GsonConvert
import com.yzq.common.ui.BaseMvpActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.NewsAdapter
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import com.yzq.kotlincommon.data.NewsBean
import com.yzq.kotlincommon.mvp.presenter.MainPresenter
import com.yzq.kotlincommon.mvp.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseMvpActivity<MainView, MainPresenter>(), MainView, BaseQuickAdapter.OnItemClickListener, View.OnClickListener {


    private lateinit var adapter: NewsAdapter


    override fun initInject() {

        DaggerMainComponent.builder().build().inject(this)

    }

    override fun getContentLayoutId(): Int {
        return R.layout.activity_main
    }

    private lateinit var imagePicker: DefaultImagePicker

    override fun initWidget() {
        super.initWidget()
        initToolbar(toolbar, "新闻")
        recy.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener(this)


        initCompressImgPresenter()

    }


    override fun initData() {
        super.initData()
        showLoadding()

        presenter.requestData()


    }


    override fun requestSuccess(data: List<NewsBean.Data>) {

        if (data.size > 0) {
            showData(data)
        } else {
            showContent()
        }


    }

    private fun showData(data: List<NewsBean.Data>) {

        adapter = NewsAdapter(R.layout.item_news_content, data)
        recy.adapter = adapter
        adapter.setOnItemClickListener(this)


        showContent()
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View?, position: Int) {


        var data = GsonConvert.toJson(adapter.data.get(position))
        LogUtils.i(data)
    }


    override fun onClick(v: View) {

        when (v.id) {
            R.id.fab -> selectImg()
        }


    }

    private fun selectImg() {

//        var intent = Intent()
//        intent.setClass(this, ImageActivity::class.java)
//        startActivity(intent)


        imagePicker = RxImagePicker.Builder().with(this)
                .build().create()

        imagePicker.openCamera().subscribe({

            compressImgPresenter.compressImg(getRealFilePath(this@MainActivity, it.uri)!!)
        })
    }


    override fun compressImgSuccess(path: String) {
        super.compressImgSuccess(path)


    }

    fun getRealFilePath(context: Context, uri: Uri): String? {

        val scheme = uri!!.getScheme()
        var data: String? = null
        if (scheme == null)
            data = uri!!.getPath()
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri!!.getPath()
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.getContentResolver().query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor!!.moveToFirst()) {
                    val index = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor!!.getString(index)
                    }
                }
                cursor!!.close()
            }
        }
        return data
    }

    override fun showContent() {

        stateView.hide()
        recy.visibility = View.VISIBLE
    }

    override fun showLoadding() {

        stateView.showLoading()
        recy.visibility = View.GONE
    }

    override fun showError(msg: String) {
        stateView.showError(msg)
        recy.visibility = View.GONE
    }


}
