package com.yzq.kotlincommon.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.databinding.ActivityBindingDelegateBinding

@Route(path = RoutePath.Main.VIEW_BINDING_DELEGATE)
class BindingDelegateActivity : AppCompatActivity() {

    private val binding by viewbind(ActivityBindingDelegateBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_binding_delegate)
        LogUtils.i("onCreate")
//        setContentView(binding.root)
//        LogUtils.i("setContentView")
        val toolbar = binding.includedToolbar.toolbar.apply {
            LogUtils.i("apply")
            title = "binding委托"
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnTest.setOnClickListener {

            LogUtils.i("binding生效了")
        }
    }

    /**
     * Toolbar的返回按钮
     *
     */
    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
