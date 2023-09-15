package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanResult
import android.view.View
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import com.hjq.permissions.Permission
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.extend.setOnThrottleTimeClick
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityBlueToothBinding
import com.yzq.kotlincommon.databinding.ItemBluetoothBinding
import com.yzq.kotlincommon.manager.BluetoothScanner
import com.yzq.logger.Logger
import com.yzq.materialdialog.showBaseDialog
import com.yzq.permission.getPermissions


@Route(path = RoutePath.Main.BLUETOOTH)
class BlueToothActivity : BaseActivity(), BluetoothScanner.ScanerCallback {

    private val binding by viewbind(ActivityBlueToothBinding::inflate)

    private var unPairDeviceList = mutableSetOf<BluetoothDevice>()
    private val bluetoothLeScanner = BluetoothScanner(this)

    @SuppressLint("MissingPermission")
    override fun initWidget() {
        binding.run {
            initToolbar(includedToolbar.toolbar, "蓝牙")

            ivRefresh.setOnThrottleTimeClick {
                bluetoothLeScanner.startScan()
            }

            rvPair
                .linear()
                .divider(R.drawable.item_divider)
                .setup {
                    addType<BluetoothDevice>(R.layout.item_bluetooth)
                    onBind {
                        val bluetoothDevice = getModel<BluetoothDevice>()
                        val itemBinding = getBinding<ItemBluetoothBinding>()
                        itemBinding.tvName.text =
                            "${bluetoothDevice.name} (${bluetoothDevice.address})"
                    }
                }

            rvUnpair
                .linear()
                .divider(R.drawable.item_divider)
                .setup {
                    addType<BluetoothDevice>(R.layout.item_bluetooth)
                    onBind {
                        val bluetoothDevice = getModel<BluetoothDevice>()
                        val itemBinding = getBinding<ItemBluetoothBinding>()
                        itemBinding.tvName.text =
                            "${bluetoothDevice.name}(${bluetoothDevice.address})"
                    }
                }


        }


    }


    override fun initData() {
        scanBlueTooth()
    }


    private fun scanBlueTooth() {
        /*获取蓝牙和位置信息权限*/
        getPermissions(
            Permission.BLUETOOTH_SCAN,
            Permission.BLUETOOTH_CONNECT,
            Permission.BLUETOOTH_ADVERTISE
        ) {
            bluetoothLeScanner.startScan()
        }
    }

    override fun startScan() {
        changeScanUi()
    }

    override fun stopScan() {
        changeScanUi()
    }

    override fun onBondedDevicesResults(bondedDevices: Set<BluetoothDevice>) {

        bondedDevices.forEach {
            Logger.i("已配对的设备：${getDeviceInfo(it)}")
        }
        /*已配对列表*/
        binding.rvPair.models = bondedDevices.toList()
    }

    private fun changeScanUi() {
        binding.progress.visibility = if (bluetoothLeScanner.isScanning) View.VISIBLE else View.GONE
        binding.ivRefresh.visibility =
            if (bluetoothLeScanner.isScanning) View.GONE else View.VISIBLE

    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothLeScanner.stopScan()
    }


    override fun onBluetoothDisabled() {
        showBaseDialog("提示", "蓝牙未开启")
    }

    @SuppressLint("MissingPermission")
    override fun onBatchScanResults(results: MutableList<ScanResult>?) {
        Logger.i("onBatchScanResults:${results?.size}")

        if (results.isNullOrEmpty()) {
            return
        }

        val resultList =
            results.map { it.device }.filter { it.name != null }
        Logger.i("resultList:${resultList.size}")

        unPairDeviceList.addAll(resultList)
        Logger.i("unPairDeviceList:${unPairDeviceList.size}")

        /*通知数据更新*/
        if (binding.rvUnpair.models.isNullOrEmpty()) {
            binding.rvUnpair.models = unPairDeviceList.toList()
        } else {
            binding.rvUnpair.setDifferModels(unPairDeviceList.toList())
        }
    }

    @SuppressLint("MissingPermission")
    override fun connectDevice(profile: Int, proxy: BluetoothProfile?) {
        Logger.i("connectDevice:${proxy?.connectedDevices?.size}")
        proxy?.connectedDevices?.forEach {
            Logger.i("已连接的设备：${getDeviceInfo(it)}")
            changeConnectUi(it)
        }

    }

    private fun changeConnectUi(it: BluetoothDevice?) {
        binding.rvPair.models?.forEach { device ->
            if (device is BluetoothDevice) {

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceInfo(device: BluetoothDevice): String {
        val sb = StringBuilder()
            .appendLine("name:${device.name}")
            .appendLine("address:${device.address}")
            .appendLine("type:${device.type}")
            .appendLine("bondState:${device.bondState}")
            .appendLine("uuids:${device.uuids}")
            .appendLine("bluetoothClass:${device.bluetoothClass}")
            .appendLine("deviceClass:${device.bluetoothClass.deviceClass}")
            .appendLine("majorDeviceClass:${device.bluetoothClass.majorDeviceClass}")

        return sb.toString()

    }
}