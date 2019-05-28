package com.yzq.gao_de_map.ui

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.common.net.GsonConvert
import com.yzq.common.ui.BaseActivity
import com.yzq.gao_de_map.R
import com.yzq.gao_de_map.dagger.DaggerGaoDeComponent
import com.yzq.gao_de_map.data.LocationBean
import com.yzq.gao_de_map.mvp.model.LocationSignModel
import com.yzq.gao_de_map.mvp.view.LocationView
import com.yzq.gao_de_map.utils.MapPermissionUtils
import kotlinx.android.synthetic.main.activity_gao_de.*
import javax.inject.Inject

@Route(path = RoutePath.GaoDe.GAO_DE)
class GaoDeActivity : BaseActivity(), LocationView {


    @Inject
    lateinit var locationSignModel: LocationSignModel

    override fun getContentLayoutId(): Int {

        return R.layout.activity_gao_de
    }

    override fun initInject() {

        DaggerGaoDeComponent.builder().build().inject(this)

        locationSignModel.initLocation(this)


    }

    @SuppressLint("AutoDispose")
    override fun initWidget() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        initToolbar(toolbar, "高德")

        btn_location.setOnClickListener {

            MapPermissionUtils.checkLocationPermission(true, this)
                    .subscribe {

                        locationSignModel.startLocation()

                    }
        }

    }


    override fun locationSuccess(location: LocationBean) {
        tv_location_result.text = GsonConvert.toJson(location)


    }

    override fun locationFailed(locationDetail: String) {

        tv_location_result.text = locationDetail
    }


}
