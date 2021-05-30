package com.yzq.gao_de_map

import android.annotation.SuppressLint
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.yzq.gao_de_map.databinding.ActivityGaoDeBinding
import com.yzq.gao_de_map.utils.MapPermissionUtils
import com.yzq.lib_base.ui.activity.BaseVbVmActivity

@Route(path = com.yzq.common.constants.RoutePath.GaoDe.GAO_DE)
class GaoDeActivity : BaseVbVmActivity<ActivityGaoDeBinding, LocationSignViewModel>() {


    override fun getViewBinding() = ActivityGaoDeBinding.inflate(layoutInflater)


    override fun getViewModelClass(): Class<LocationSignViewModel> =
        LocationSignViewModel::class.java


    @SuppressLint("AutoDispose")
    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        initToolbar(toolbar, "高德")

        binding.btnLocation.setOnClickListener {

            MapPermissionUtils.checkLocationPermission(true, this) {
                stateViewManager.showLoadingDialog("正在获取位置信息")

                vm.startLocation()
            }

        }


    }


    override fun observeViewModel() {
        vm.locationData.observe(this,
            Observer<AMapLocation> { t ->
                if (t.errorCode == 0) {
                    binding.tvLocationResult.text = t.address
                } else {
                    binding.tvLocationResult.text = t.locationDetail
                }

                stateViewManager.dismissLoadingDialog()
            })

    }


}
