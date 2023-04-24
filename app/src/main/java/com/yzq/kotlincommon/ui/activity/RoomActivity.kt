package com.yzq.kotlincommon.ui.activity

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.data_base.User
import com.yzq.coroutine.safety_coroutine.launchSafety
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityRoomBinding
import com.yzq.kotlincommon.databinding.ItemRoomBinding
import com.yzq.kotlincommon.view_model.RoomViewModel
import com.yzq.materialdialog.showInputDialog
import kotlinx.coroutines.delay

@Route(path = RoutePath.Main.ROOM)
class RoomActivity : BaseActivity() {

    private val binding by viewbind(ActivityRoomBinding::inflate)
    private val vm: RoomViewModel by viewModels()
    private var isAdd: Boolean = false

    override fun initWidget() {
        initToolbar(binding.includedToolbar.toolbar, "Room")
        binding.apply {

            recy.linear()
                .divider(R.drawable.item_divider)
                .setup {
                    addType<User>(R.layout.item_room)
                    onBind {
                        val itemBinding = getBinding<ItemRoomBinding>()
                        val user = getModel<User>()
                        itemBinding.tvUser.text = buildString {
                            append(user.id.toString())
                            append("---")
                            append(user.name)
                        }
                    }

                    R.id.tv_delete.onClick {
                        vm.deleteUser(getModel())
                    }

                    R.id.tv_user.onClick {
                        showInputDialog(title = "修改") { dialog, input ->
                            vm.updateUser(getModel<User>().id, input.toString())
                        }
                    }
                }

            fabAdd.setOnClickListener {
                isAdd = true
                vm.insertUser()
            }

            fabDelete.setOnClickListener {
                isAdd = false
                vm.clearUser()
            }
        }
    }

    override fun observeViewModel() {

        vm.run {
            users.observe(this@RoomActivity) {
                binding.recy.setDifferModels(it)
                /*延时滚动到最底部*/
                if (isAdd) {
                    scrollRecy(it.size)
                }
            }
        }
    }

    private fun scrollRecy(size: Int) {

        lifecycleScope.launchSafety {
            delay(100)
            binding.recy.scrollToPosition(size - 1)
        }
    }
}
