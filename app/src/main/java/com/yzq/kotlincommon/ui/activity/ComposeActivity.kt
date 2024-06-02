package com.yzq.kotlincommon.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.therouter.router.Route
import com.yzq.base.theme.LightBlue300
import com.yzq.common.constants.RoutePath
import com.yzq.coroutine.safety_coroutine.launchSafety
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * @description: ComposeActivity
 * @author : yuzhiqiang
 */

@Route(path = RoutePath.Main.COMPOSE)
class ComposeActivity : ComponentActivity() {

    // 定义一个可变的状态
    val name = mutableStateOf("Compose")

    // 通过by关键字，将数据的读写委托给mutableStateOf，实际上执行的mutableStateOf扩展的getValue和setValue方法（从导包androidx.compose.runtime.getValue，androidx.compose.runtime.setValue可以看出来），在开发时比较方便，不需要手动调用getValue和setValue方法
    var age by mutableIntStateOf(18)


    // 对于集合类型的数据，使用mutableStateListOf，类似的还有mutableStateMapOf，mutableStateSetOf，mutableStateArrayOf等
    private val userList = mutableStateListOf("Xeon", "Yuzhiqiang", "Compose")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContent是Compose的一个函数，用于设置UI界面
        setContent {

            Column(Modifier.fillMaxWidth()) {
                Text(name.value)
                Text("${age}")
                ShowStrLength(name.value)
                Button(onClick = {
                    //生成一个随机数，添加到userList中
                    val randomNumber = Random.nextInt().toString()
                    userList.add(randomNumber)
                }) {
                    Text("Add")
                }

                for (name in userList) {
                    Text(name)
                }

                OutlinedTextField(value = name.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, LightBlue300, shape = RectangleShape),
                    onValueChange = {
                        name.value = it
                    })
            }
        }

        lifecycleScope.launchSafety {
            delay(2000)
            name.value = "yuzhiqiang"
            age = 30
        }
    }


}

@Composable
fun ShowStrLength(value: String) {
    /**
     * remember 起到缓存作用，防止重组导致变量重新初始化，只要是在Compose函数内部的变量，理论上都要使用remember进行缓存
     * 参数value是需要缓存的变量，只要value发生变化，size也会发生变化，如果value不发生变化，size也不会发生变化，以避免重组
     */
    val size = remember(value) { value.length }
    Text("The length of the string is $size")
}
