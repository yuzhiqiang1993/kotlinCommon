package com.yzq.kotlincommon.ui

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseActivity
import com.yzq.gao_de_map.data.LocationBean
import com.yzq.gao_de_map.model.LocationSignModel
import com.yzq.gao_de_map.utils.MapPermissionUtils
import com.yzq.gao_de_map.view.LocationView
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.dagger.DaggerMainComponent
import kotlinx.android.synthetic.main.activity_gao_de.*
import javax.inject.Inject


/**
 * @description: 高德地图
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:38
 *
 */

@Route(path = RoutePath.Main.GAO_DE)
class GaoDeActivity : BaseActivity(), LocationView {


    @Inject
    lateinit var locationSignModel: LocationSignModel

    override fun getContentLayoutId(): Int {

        return R.layout.activity_gao_de
    }


    override fun initInject() {
        super.initInject()

        DaggerMainComponent.builder().build().inject(this)

        locationSignModel.initLocation(this)

    }


    @SuppressLint("AutoDispose")
    override fun initWidget() {
        super.initWidget()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "高德")
        btn_location.setOnClickListener {

            MapPermissionUtils.checkLocationPermission(activity = this)
                    .subscribe {
                        tv_location.text = "开始定位"
                        locationSignModel.startLocation()
                    }


        }

    }


    override fun locationSuccess(location: LocationBean) {
        tv_location.text = Gson().toJson(location)
    }

    override fun locationFailed(locationDetail: String) {
        tv_location.text = "定位失败"
    }


    override fun onDestroy() {
        super.onDestroy()

        locationSignModel.destroyLocation()
    }


}
