package com.yzq.kotlincommon.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.yanzhenjie.permission.runtime.Permission
import com.yzq.common.constants.RoutePath
import com.yzq.common.extend.requestPermission
import com.yzq.common.extend.showBaseDialog
import com.yzq.common.extend.transform
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import com.yzq.zxinglibrary.common.Constant
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_zxing.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


/**
 * @description: 二维码扫描
 * @author : yzq
 * @date   : 2018/12/5
 * @time   : 10:05
 *
 */

@Route(path = RoutePath.Main.ZXING)
class ZxingActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {

        return R.layout.activity_zxing
    }


    override fun initWidget() {
        super.initWidget()

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Zxing")

        btn_scan.setOnClickListener { excuteZxing() }

        btn_jsoup.setOnClickListener {
            getLicenseInfo()
        }

    }

    val REQUEST_LICENSE_CODE = 666
    @SuppressLint("AutoDispose", "CheckResult")
    private fun getLicenseInfo() {
        requestPermission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { hasPermission ->
                    val intent = Intent(this, CaptureActivity::class.java)
                    startActivityForResult(intent, REQUEST_LICENSE_CODE)
                }
    }

    val REQUEST_CODE_SCAN = 555
    @SuppressLint("CheckResult", "AutoDispose")
    private fun excuteZxing() {
        requestPermission(Permission.CAMERA, Permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { hasPermission ->

                    val intent = Intent(this, CaptureActivity::class.java)
                    val zxingConfig = ZxingConfig()
                    zxingConfig.isFullScreenScan = false
                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, zxingConfig);
                    startActivityForResult(intent, REQUEST_CODE_SCAN)
                }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_SCAN -> {
                if (data != null) {
                    val content = data.getStringExtra(Constant.CODED_CONTENT)
                    tv_result.setText(content)
                }


            }

            REQUEST_LICENSE_CODE -> {

                if (data != null) {
                    val content = data.getStringExtra(Constant.CODED_CONTENT)

                    if (RegexUtils.isURL(content)) {
                        LogUtils.i("扫描的是营业执照")

                        /*开始提取数据*/
                        parseData(content)

                    } else {
                        ToastUtils.showShort("请扫描营业执照上的二维码！")
                    }
                }


            }
        }
    }

    /*开始提取数据*/
    @SuppressLint("SetTextI18n")
    private fun parseData(url: String) {

        showLoadingDialog("正在解析....")

        Observable.create<Document> {

            try {
                it.onNext(Jsoup.connect(url).get())
            } catch (e: Exception) {

                it.onNext(Document("aaa"))
            }
        }.transform(this).subscribe {

            LogUtils.i("网页标题：" + it.title())

            //var node=it.select(".tableYyzz tbody tr:first-child td:first-child i")
            val code = it.select(".tableYyzz tbody tr:first-child td:first-child i").text()
            val name = it.select(".tableYyzz tbody tr:first-child td:nth-child(2) i").text()
            val people = it.select(".tableYyzz tbody tr:nth-child(2) td:nth-child(2) i").text()

            LogUtils.i("统一社会信用代码：" + code)
            LogUtils.i("企业名称：" + name)
            LogUtils.i("法定代表人：" + people)

            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(name) || TextUtils.isEmpty(people)) {
                dismissLoadingDialog()
                showBaseDialog(message = "请扫描营业执照上的二维码！")
                return@subscribe
            } else {
                tv_result.setText("""
                统一社会信用代码:${code}
                企业名称:${name}
                法定代表人:${people}
            """.trimIndent())
                dismissLoadingDialog()

            }


        }


    }
}
