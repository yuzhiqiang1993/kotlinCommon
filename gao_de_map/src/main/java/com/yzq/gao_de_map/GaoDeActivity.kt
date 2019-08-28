package com.yzq.gao_de_map

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.yzq.common.ui.BaseMvvmActivity
import com.yzq.gao_de_map.utils.MapPermissionUtils
import kotlinx.android.synthetic.main.activity_gao_de.*

@Route(path = com.yzq.data_constants.constants.RoutePath.GaoDe.GAO_DE)
class GaoDeActivity : BaseMvvmActivity<LocationSignViewModel>() {


    override fun getViewModelClass(): Class<LocationSignViewModel> = LocationSignViewModel::class.java


    override fun getContentLayoutId(): Int {

        return R.layout.activity_gao_de
    }


    @SuppressLint("AutoDispose")
    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        initToolbar(toolbar, "高德")

        btn_location.setOnClickListener {

            MapPermissionUtils.checkLocationPermission(true, this)
                    .subscribe { hasPermission ->

                        showLoadingDialog("正在获取位置信息")

                        vm.startLocation()

                    }
        }


    }


    override fun observeViewModel() {
        vm.locationData.observe(this, object : Observer<AMapLocation> {
            override fun onChanged(t: AMapLocation) {

                if (t.errorCode == 0) {
                    tv_location_result.text = t.address
                } else {
                    tv_location_result.text = t.locationDetail
                }

                dismissLoadingDialog()


            }
        })

    }


}
