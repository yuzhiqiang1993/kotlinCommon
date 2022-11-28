package com.yzq.gao_de_map

import androidx.activity.viewModels
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.gao_de_map.databinding.ActivityGaoDeBinding
import com.yzq.gao_de_map.utils.MapPermissionUtils

@Route(path = com.yzq.common.constants.RoutePath.GaoDe.GAO_DE)
class GaoDeActivity : BaseActivity() {

    private val binding by viewbind(ActivityGaoDeBinding::inflate)
    private val vm: LocationSignViewModel by viewModels()

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "高德")

        binding.btnLocation.setOnClickListener {
            MapPermissionUtils.checkLocationPermission(true, this) {
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
        }
    }
}
