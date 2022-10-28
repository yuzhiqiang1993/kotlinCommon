package com.yzq.gao_de_map

import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.gao_de_map.databinding.ActivityGaoDeBinding
import com.yzq.gao_de_map.utils.MapPermissionUtils
import com.yzq.lib_base.ui.activity.BaseVmActivity

@Route(path = com.yzq.common.constants.RoutePath.GaoDe.GAO_DE)
class GaoDeActivity : BaseVmActivity<ActivityGaoDeBinding, LocationSignViewModel>() {


    override fun createBinding(): ActivityGaoDeBinding =
        ActivityGaoDeBinding.inflate(layoutInflater)


    override fun getViewModelClass(): Class<LocationSignViewModel> =
        LocationSignViewModel::class.java


    override fun initWidget() {


        initToolbar(binding.includedToolbar.toolbar, "高德")


        binding.btnLocation.setOnClickListener {

            MapPermissionUtils.checkLocationPermission(true, this) {
                stateViewManager.showLoadingDialog("正在获取位置信息")

                vm.startLocation()
            }

        }


    }


    override fun observeViewModel() {
        vm.locationData.observe(
            this
        ) { t ->
            if (t.errorCode == 0) {
                binding.tvLocationResult.text = t.address
            } else {
                binding.tvLocationResult.text = t.locationDetail
            }

            stateViewManager.dismissLoadingDialog()
        }

    }


}
