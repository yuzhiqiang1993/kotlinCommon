package com.yzq.kotlincommon.ui

import android.support.v7.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.Dialog
import com.yzq.kotlincommon.R
import kotlinx.android.synthetic.main.activity_date_time.*


@Route(path = RoutePath.Main.DATE_TIME)
class DateTimeActivity : BaseActivity() {


    override fun getContentLayoutId(): Int {

        return R.layout.activity_date_time
    }


    override fun initWidget() {
        super.initWidget()

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "日期时间")


        selectYearBtn.setOnClickListener { Dialog.selectYear().subscribe { yearTv.text = it } }
        selectDateBtn.setOnClickListener { Dialog.selectDate().subscribe { dateTv.text = it } }
        selectTimeBtn.setOnClickListener { Dialog.selectHourAndMinute().subscribe { timeTv.text = it } }
    }
}
