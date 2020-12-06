package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityZxingBinding
import com.yzq.lib_base.ui.BaseViewBindingActivity
import com.yzq.lib_materialdialog.showBaseDialog
import com.yzq.lib_permission.getPermissions
import com.yzq.lib_rx.transform
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import com.yzq.zxinglibrary.common.Constant
import io.reactivex.Observable
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
class ZxingActivity : BaseViewBindingActivity<ActivityZxingBinding>() {
    override fun getViewBinding() = ActivityZxingBinding.inflate(layoutInflater)
    override fun initWidget() {
        super.initWidget()

        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Zxing")

        binding.btnScan.setOnClickListener { excuteZxing() }

        binding.btnJsoup.setOnClickListener {
            getLicenseInfo()
        }

    }

    private val requestLicenseCode = 666
    private fun getLicenseInfo() {
        getPermissions(PermissionConstants.CAMERA, PermissionConstants.STORAGE) {

            val intent = Intent(this, CaptureActivity::class.java)
            startActivityForResult(intent, requestLicenseCode)
        }


    }

    private val requestCodeScan = 555
    private fun excuteZxing() =
        getPermissions(PermissionConstants.CAMERA, PermissionConstants.STORAGE) {
            val intent = Intent(this, CaptureActivity::class.java)
            val zxingConfig = ZxingConfig()
            zxingConfig.isFullScreenScan = false
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, zxingConfig)
            startActivityForResult(intent, requestCodeScan)
        }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            requestCodeScan -> {
                if (data != null) {
                    val content = data.getStringExtra(Constant.CODED_CONTENT)
                    binding.tvResult.text = content
                }


            }

            requestLicenseCode -> {

                if (data != null) {
                    val content = data.getStringExtra(Constant.CODED_CONTENT)

                    if (RegexUtils.isURL(content)) {
                        LogUtils.i("扫描的是营业执照")

                        /*开始提取数据*/
                        parseData(content!!)

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

            LogUtils.i("统一社会信用代码：$code")
            LogUtils.i("企业名称：$name")
            LogUtils.i("法定代表人：$people")

            if (TextUtils.isEmpty(code) || TextUtils.isEmpty(name) || TextUtils.isEmpty(people)) {
                dismissLoadingDialog()
                showBaseDialog(message = "请扫描营业执照上的二维码！")
                return@subscribe
            } else {
                binding.tvResult.text = """
                            统一社会信用代码:${code}
                            企业名称:${name}
                            法定代表人:${people}
                        """.trimIndent()
                dismissLoadingDialog()

            }


        }


    }


}
