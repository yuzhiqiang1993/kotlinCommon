package com.yzq.data.bluetooth

import android.bluetooth.BluetoothClass
import java.util.Locale


/**
 * @description: 蓝牙设备实体类
 * @author : yuzhiqiang
 */

data class BlueToothItem(
    var name: String = "",//蓝牙的名称
    var address: String = "",//地址
    var type: Int = -1,//类型
    var bondState: Int = -1,//绑定状态
    var deviceClass: Int = -1,//设备类型
    var majorDeviceClass: Int = -1,//主设备类型
    var connected: Boolean = false,//连接状态
    var uuids: List<String> = emptyList()//服务UUID
)


fun BlueToothItem.deviceDesc(): String {


    val specificDesc = when (deviceClass) {
        //计算机设备
        BluetoothClass.Device.COMPUTER_UNCATEGORIZED -> "未分类计算机"
        BluetoothClass.Device.COMPUTER_DESKTOP -> "台式电脑"
        BluetoothClass.Device.COMPUTER_SERVER -> "服务器"
        BluetoothClass.Device.COMPUTER_LAPTOP -> "笔记本电脑"
        BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA -> "手持PC/PDA"
        BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA -> "掌上PC/PDA"
        BluetoothClass.Device.COMPUTER_WEARABLE -> "可穿戴电脑"

        //电话设备
        BluetoothClass.Device.PHONE_UNCATEGORIZED -> "未分类电话"
        BluetoothClass.Device.PHONE_CELLULAR -> "手机"
        BluetoothClass.Device.PHONE_CORDLESS -> "无绳电话"
        BluetoothClass.Device.PHONE_SMART -> "智能手机"
        BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY -> "调制解调器或网关"
        BluetoothClass.Device.PHONE_ISDN -> "ISDN终端"

        //音视频设备
        BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED -> "未分类音频/视频"
        BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET -> "可穿戴耳机"
        BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE -> "免提设备"
        BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE -> "麦克风"
        BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER -> "扬声器"
        BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES -> "耳机"
        BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO -> "便携式音频"
        BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO -> "汽车音频"
        BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX -> "机顶盒"
        BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO -> "HIFI音频"
        BluetoothClass.Device.AUDIO_VIDEO_VCR -> "VCR"
        BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA -> "摄像机"
        BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER -> "摄像机"
        BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR -> "视频监视器"
        BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER -> "视频显示和扬声器"
        BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING -> "视频会议"
        BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY -> "视频游戏玩具"

        //穿戴设备
        BluetoothClass.Device.WEARABLE_UNCATEGORIZED -> "未分类可穿戴设备"
        BluetoothClass.Device.WEARABLE_WRIST_WATCH -> "手表"
        BluetoothClass.Device.WEARABLE_PAGER -> "寻呼机"
        BluetoothClass.Device.WEARABLE_JACKET -> "夹克"
        BluetoothClass.Device.WEARABLE_HELMET -> "头盔"
        BluetoothClass.Device.WEARABLE_GLASSES -> "眼镜"

        //玩具设备
        BluetoothClass.Device.TOY_UNCATEGORIZED -> "未分类玩具"
        BluetoothClass.Device.TOY_ROBOT -> "机器人"
        BluetoothClass.Device.TOY_VEHICLE -> "车辆玩具"
        BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE -> "玩偶和动作人物"
        BluetoothClass.Device.TOY_CONTROLLER -> "遥控器"
        BluetoothClass.Device.TOY_GAME -> "游戏机"

        //健康设备
        BluetoothClass.Device.HEALTH_UNCATEGORIZED -> "未分类健康设备"
        BluetoothClass.Device.HEALTH_BLOOD_PRESSURE -> "血压计"
        BluetoothClass.Device.HEALTH_THERMOMETER -> "温度计"
        BluetoothClass.Device.HEALTH_WEIGHING -> "称重器"
        BluetoothClass.Device.HEALTH_GLUCOSE -> "血糖仪"
        BluetoothClass.Device.HEALTH_PULSE_OXIMETER -> "脉搏血氧仪"
        BluetoothClass.Device.HEALTH_PULSE_RATE -> "脉搏计"
        BluetoothClass.Device.HEALTH_DATA_DISPLAY -> "健康数据显示器"

        //外围设备
        BluetoothClass.Device.PERIPHERAL_NON_KEYBOARD_NON_POINTING -> "非键盘和指针设备"
        BluetoothClass.Device.PERIPHERAL_KEYBOARD -> "键盘"
        BluetoothClass.Device.PERIPHERAL_POINTING -> "指针设备"
        BluetoothClass.Device.PERIPHERAL_KEYBOARD_POINTING -> "键盘和指针设备"

        else -> {
            null
        }
    }
    // 如果能够获取具体的设备类别描述，则优先使用
    specificDesc?.let { return it }

    //主设备类型
    val majorDesc = when (majorDeviceClass) {
        BluetoothClass.Device.Major.MISC -> "杂项"
        BluetoothClass.Device.Major.COMPUTER -> "计算机"
        BluetoothClass.Device.Major.PHONE -> "电话"
        BluetoothClass.Device.Major.NETWORKING -> "网络"
        BluetoothClass.Device.Major.AUDIO_VIDEO -> "音频/视频"
        BluetoothClass.Device.Major.PERIPHERAL -> "外围设备"
        BluetoothClass.Device.Major.IMAGING -> "成像设备"
        BluetoothClass.Device.Major.WEARABLE -> "可穿戴设备"
        BluetoothClass.Device.Major.TOY -> "玩具"
        BluetoothClass.Device.Major.HEALTH -> "健康设备"
        BluetoothClass.Device.Major.UNCATEGORIZED -> "未分类"
        else -> "未分类"
    }

    // 根据设备名称进一步判断类型
    return guessDeviceTypeFromName(name) ?: majorDesc
}

fun guessDeviceTypeFromName(name: String): String? {
    val lowercaseName = name.lowercase(Locale.getDefault())
    val guessName = when {
        "car" in lowercaseName || "vehicle" in lowercaseName -> "汽车设备"
        "phone" in lowercaseName || "mobile" in lowercaseName -> "电话"
        "macbook" in lowercaseName || "laptop" in lowercaseName || "notebook" in lowercaseName -> "笔记本电脑"
        "pc" in lowercaseName || "desktop" in lowercaseName -> "台式电脑"
        "audio" in lowercaseName || "sound" in lowercaseName || "music" in lowercaseName -> "音频设备"
        "video" in lowercaseName -> "视频设备"
        "watch" in lowercaseName -> "手表"
        "wearable" in lowercaseName || "band" in lowercaseName -> "可穿戴设备"
        "toy" in lowercaseName -> "玩具"
        "health" in lowercaseName || "heart" in lowercaseName || "fit" in lowercaseName -> "健康设备"
        "headset" in lowercaseName || "earbud" in lowercaseName || "earphone" in lowercaseName -> "耳机"
        "speaker" in lowercaseName -> "扬声器"
        "mouse" in lowercaseName -> "鼠标"
        "keyboard" in lowercaseName -> "键盘"
        "camera" in lowercaseName -> "相机"
        "printer" in lowercaseName -> "打印机"
        "router" in lowercaseName || "hub" in lowercaseName || "switch" in lowercaseName -> "网络设备"
        "game" in lowercaseName || "console" in lowercaseName -> "游戏设备"
        "drone" in lowercaseName -> "无人机"
        "tracker" in lowercaseName -> "追踪器"
        "scale" in lowercaseName -> "秤"
        "lamp" in lowercaseName || "light" in lowercaseName -> "灯光设备"
        "mac" in lowercaseName || "apple" in lowercaseName -> "苹果设备"
        else -> null
    }

    guessName?.let { return "${guessName} 【猜测】" }

    return guessName

}
