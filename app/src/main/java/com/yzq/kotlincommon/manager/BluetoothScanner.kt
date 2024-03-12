package com.yzq.kotlincommon.manager

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import com.yzq.application.AppContext
import com.yzq.coroutine.safety_coroutine.postDelayed
import com.yzq.logger.Logger
import java.util.concurrent.atomic.AtomicBoolean

class BluetoothScanner(private val callback: ScanerCallback) {

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        (AppContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    private val scanning = AtomicBoolean(false)


    val isScanning: Boolean
        get() = scanning.get()


    private val internalScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Logger.i("onScanResult,callbackType:$callbackType")
            callback.onScanResult(callbackType, result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            Logger.i("onBatchScanResults,results.size:${results?.size}")
            callback.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            callback.onScanFailed(errorCode)
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan() {
        if (!bluetoothAdapter.isEnabled) {
            callback.onBluetoothDisabled()
            return
        }
        if (scanning.get()) {
            return
        }
        scanning.set(true)
        callback.startScan()

        callback.onBondedDevicesResults(bluetoothAdapter.bondedDevices)


        val settings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(1000 * 2).build()



        bluetoothAdapter.bluetoothLeScanner?.startScan(null, settings, internalScanCallback)

        postDelayed(deleyMillis = 20 * 1000) {
            stopScan()
        }


    }


    fun refreshConnectedDevices(callback: ScanerCallback) {/*获取哪个蓝牙处于连接状态*/
        bluetoothAdapter.getProfileProxy(AppContext, object : BluetoothProfile.ServiceListener {
            override fun onServiceDisconnected(profile: Int) {
                //断开连接的设备
                callback.disonnectDevice(profile)
            }

            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                //已连接的设备
                callback.connectDevice(profile, proxy)
            }
        }, BluetoothProfile.HEADSET)
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {
        if (scanning.get()) {
            scanning.set(false)
            bluetoothAdapter.bluetoothLeScanner?.stopScan(internalScanCallback)
            callback.stopScan()
        }
    }

    interface ScanerCallback {
        fun startScan() {}
        fun stopScan() {}
        fun onBluetoothDisabled() {}
        fun onScanResult(callbackType: Int, result: ScanResult) {}
        fun onBatchScanResults(results: MutableList<ScanResult>?) {}
        fun onScanFailed(errorCode: Int) {}
        fun onBondedDevicesResults(bondedDevices: Set<BluetoothDevice>) {

        }

        fun disonnectDevice(profile: Int) {


        }

        fun connectDevice(profile: Int, proxy: BluetoothProfile?) {


        }

    }
}
