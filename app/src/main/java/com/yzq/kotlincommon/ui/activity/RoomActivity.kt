package com.yzq.kotlincommon.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setDifferModels
import com.drake.brv.utils.setup
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.data_base.User
import com.yzq.coroutine.scope.lifeScope
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityRoomBinding
import com.yzq.kotlincommon.databinding.ItemRoomBinding
import com.yzq.kotlincommon.view_model.RoomViewModel
import com.yzq.materialdialog.showInputDialog
import kotlinx.coroutines.delay

@Route(path = RoutePath.Main.ROOM)
class RoomActivity :
    BaseVmActivity<RoomViewModel>() {

    private val binding by viewbind(ActivityRoomBinding::inflate)

    private var isAdd: Boolean = false

    override fun getViewModelClass(): Class<RoomViewModel> = RoomViewModel::class.java

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
                        itemBinding.tvUser.setText(
                            buildString {
                                append(user.id.toString())
                                append("---")
                                append(user.name)
                            }
                        )
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

        lifeScope {
            delay(100)
            binding.recy.scrollToPosition(size - 1)
        }
    }
}
