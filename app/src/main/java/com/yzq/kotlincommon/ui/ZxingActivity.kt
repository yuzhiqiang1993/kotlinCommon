package com.yzq.kotlincommon.ui

import android.annotation.SuppressLint
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.common.permission.PermissionRequester
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.common.Constant
import kotlinx.android.synthetic.main.activity_zxing.*

@Route(path = RoutePath.Main.ZXING)
class ZxingActivity : BaseActivity() {
    override fun getContentLayoutId(): Int {

        return R.layout.activity_zxing
    }


    override fun initWidget() {
        super.initWidget()

        scanBtn.setOnClickListener {

            excuteZxing()
        }
    }

    val REQUEST_CODE_SCAN = 555
    @SuppressLint("CheckResult")
    private fun excuteZxing() {
        PermissionRequester.request(com.yanzhenjie.permission.Permission.CAMERA, com.yanzhenjie.permission.Permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {

                    val intent = Intent(this, CaptureActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE_SCAN)
                }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN) {
            if (data != null) {

                val content = data.getStringExtra(Constant.CODED_CONTENT)
                resultTv.setText(content)
            }
        }
    }
}
