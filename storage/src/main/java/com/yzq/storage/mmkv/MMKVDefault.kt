package com.yzq.storage.mmkv

import com.tencent.mmkv.MMKV
import com.yzq.mmkv.MMKVReadWriteProp
import com.yzq.storage.db.User


/**
 * @description 默认的MMKV存储，如果说一个App中没啥复杂的存储需求，那么可以直接使用这个类来存储数据，不用再去创建MMKV实例了，使用起来更加方便
 * 如果需要分模块存储，建议不同模块创建不同的MMKV实例，这样更加清晰，例如：MMKVUser，MMKVSetting等
 * 官方使用文档：https://github.com/Tencent/MMKV/wiki/android_advance_cn
 * @author  yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 */

object MMKVDefault {
    /*多进程共享一份数据  不加密 MMKV 默认把文件存放在$(FilesDir)/mmkv/目录*/
    val mmkv: MMKV = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, null)

    var appFirstOpen: Boolean by MMKVReadWriteProp("appFirstOpen", true, mmkv)
    var test: User by MMKVReadWriteProp("test", User(name = "xeonyu", age = 18), mmkv)

}
