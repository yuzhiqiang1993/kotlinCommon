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
import com.yzq.common.data.bluetooth.BlueToothItem
import com.yzq.common.data.bluetooth.deviceDesc
import com.yzq.dialog.showBaseDialog
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityBlueToothBinding
import com.yzq.kotlincommon.databinding.ItemBluetoothBinding
import com.yzq.kotlincommon.manager.BluetoothScanner
import com.yzq.logger.Logger
import com.yzq.permission.getPermissions


@Route(path = RoutePath.Main.BLUETOOTH)
class BlueToothActivity : BaseActivity(), BluetoothScanner.ScanerCallback {

    private val binding by viewbind(ActivityBlueToothBinding::inflate)

    //已配对列表
    private val pairDeviceList = mutableSetOf<BlueToothItem>()

    //可用列表
    private var unPairDeviceList = mutableSetOf<BlueToothItem>()

    private val bluetoothLeScanner = BluetoothScanner(this)


    @SuppressLint("MissingPermission")
    override fun initWidget() {
        binding.run {
            initToolbar(includedToolbar.toolbar, "蓝牙")

            ivRefresh.setOnThrottleTimeClick {
                bluetoothLeScanner.startScan()
            }

            rvPair.linear().divider(R.drawable.item_divider).setup {
                addType<BlueToothItem>(R.layout.item_bluetooth)
                onBind {
                    val item = getModel<BlueToothItem>()
                    val itemBinding = getBinding<ItemBluetoothBinding>()
                    "${item.name}(${item.deviceDesc()})".also {
                        itemBinding.tvName.text = it
                    }

                    if (item.connected) {
                        itemBinding.tvName.setTextColor(resources.getColor(R.color.colorPrimary))
                        itemBinding.tvUse.visibility = View.VISIBLE
                    } else {
                        itemBinding.tvName.setTextColor(resources.getColor(R.color.black))
                        itemBinding.tvUse.visibility = View.GONE
                    }
                }
            }

            rvUnpair.linear().divider(R.drawable.item_divider).setup {
                addType<BlueToothItem>(R.layout.item_bluetooth)
                onBind {
                    val item = getModel<BlueToothItem>()
                    val itemBinding = getBinding<ItemBluetoothBinding>()

                    "${item.name}(${item.deviceDesc()})".also {
                        itemBinding.tvName.text = it
                    }


                }
            }
        }


    }


    override fun initData() {
        scanBlueTooth()
    }


    @SuppressLint("MissingPermission")
    private fun scanBlueTooth() {/*获取蓝牙和位置信息权限*/
        getPermissions(
            Permission.BLUETOOTH_SCAN, Permission.BLUETOOTH_CONNECT, Permission.BLUETOOTH_ADVERTISE
        ) {
            bluetoothLeScanner.startScan()
        }
    }

    override fun startScan() {
        changeScanUi()
    }

    @SuppressLint("MissingPermission")
    override fun stopScan() {
        changeScanUi()
    }

    @SuppressLint("MissingPermission")
    override fun onBondedDevicesResults(bondedDevices: Set<BluetoothDevice>) {

        bondedDevices.forEach {
            Logger.i("已配对的设备：${getDeviceInfo(it)}")
        }

        //已配对列表
        val bondedList = bondedDevices.map {
            BlueToothItem(
                name = it.name ?: "",
                address = it.address,
                type = it.type,
                bondState = it.bondState,
                deviceClass = it.bluetoothClass.deviceClass,
                majorDeviceClass = it.bluetoothClass.majorDeviceClass,
                connected = false,
                uuids = it.uuids?.map { it.uuid.toString() } ?: emptyList()
            )
        }

        pairDeviceList.addAll(bondedList)

        /*已配对列表*/
        binding.rvPair.models = pairDeviceList.toList()
    }

    private fun changeScanUi() {
        binding.progress.visibility = if (bluetoothLeScanner.isScanning) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.ivRefresh.visibility = if (bluetoothLeScanner.isScanning) {
            View.GONE
        } else {
            View.VISIBLE
        }
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
            results.map { it.device }.filter { it.name != null && it.name.isNotEmpty() }

        resultList.listIterator().forEach {
            Logger.i("扫描到的设备：${getDeviceInfo(it)}")
        }


        val blueToothItems = resultList.map {
            BlueToothItem(
                name = it.name ?: "",
                address = it.address,
                type = it.type,
                bondState = it.bondState,
                deviceClass = it.bluetoothClass.deviceClass,
                majorDeviceClass = it.bluetoothClass.majorDeviceClass,
                connected = false,
                uuids = it.uuids?.map { it.uuid.toString() } ?: emptyList()
            )
        }


        //把已配对的设备和已添加的设备过滤掉
        val filterList = blueToothItems.filter { item ->
            pairDeviceList.find { it.address == item.address } == null
        }

        //如果unPairDeviceList已经添加过blueToothItems中的数据，就不再添加
        if (unPairDeviceList.isNotEmpty()) {
            filterList.forEach { item ->
                if (unPairDeviceList.find { it.address == item.address } == null) {
                    unPairDeviceList.add(item)
                }
            }
        } else {
            unPairDeviceList.addAll(filterList)
        }


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
        if (proxy?.connectedDevices.isNullOrEmpty()) {
            pairDeviceList.forEach {
                it.connected = false
            }
            binding.rvPair.adapter?.notifyDataSetChanged()
            return
        }
        proxy?.connectedDevices?.forEach {
            Logger.i("已连接的设备：${getDeviceInfo(it)}")
            changeConnectUi(it)
        }
    }

    override fun disonnectDevice(profile: Int) {
        Logger.i("disonnectDevice:$profile")
    }

    private fun changeConnectUi(it: BluetoothDevice?) {

        pairDeviceList.find { item ->
            item.address == it?.address
        }?.let {
            it.connected = true
            binding.rvPair.adapter?.notifyItemChanged(pairDeviceList.indexOf(it))
        }


    }

    @SuppressLint("MissingPermission")
    private fun getDeviceInfo(device: BluetoothDevice): String {
        val sb = StringBuilder().appendLine("name:${device.name}")
            .appendLine("address:${device.address}")
            .appendLine("type:${device.type}")
            .appendLine("bondState:${device.bondState}")
            .appendLine("deviceClass:${device.bluetoothClass.deviceClass}")
            .appendLine("majorDeviceClass:${device.bluetoothClass.majorDeviceClass}")


        device.uuids?.iterator()?.forEach {
            it.uuid?.let {
                sb.appendLine("uuid:${it}")
            }
        }

        return sb.toString()

    }


    override fun onResume() {
        super.onResume()
        //前后台切换时检查下当前蓝牙是否有链接
        bluetoothLeScanner.refreshConnectedDevices(this)

    }
}