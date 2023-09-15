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
            callback.onScanResult(callbackType, result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
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
        /*获取哪个蓝牙处于连接状态*/
        bluetoothAdapter.getProfileProxy(AppContext, object : BluetoothProfile.ServiceListener {
            override fun onServiceDisconnected(profile: Int) {
                callback.disonnectDevice(profile)
            }

            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                callback.connectDevice(profile, proxy)
            }
        }, BluetoothProfile.HEADSET)


        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
            .setReportDelay(200)
//            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        bluetoothAdapter.bluetoothLeScanner?.startScan(null, settings, internalScanCallback)

        postDelayed(deleyMillis = 20 * 1000) {
            stopScan()
        }


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
