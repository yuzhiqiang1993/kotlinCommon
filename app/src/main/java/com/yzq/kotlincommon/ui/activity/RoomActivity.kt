package com.yzq.kotlincommon.ui.activity

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.yzq.base.extend.init
import com.yzq.base.ui.activity.BaseVmActivity
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.data_base.User
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.RoomAdapter
import com.yzq.kotlincommon.databinding.ActivityRoomBinding
import com.yzq.kotlincommon.view_model.RoomViewModel
import com.yzq.materialdialog.showInputDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Route(path = RoutePath.Main.ROOM)
class RoomActivity : BaseVmActivity<ActivityRoomBinding, RoomViewModel>(),
    OnItemChildClickListener {


    override fun createBinding() = ActivityRoomBinding.inflate(layoutInflater)


    private var isAdd: Boolean = false
    private val roomAdapter = RoomAdapter(R.layout.item_room, arrayListOf())

    private lateinit var operationItem: User


    override fun getViewModelClass(): Class<RoomViewModel> = RoomViewModel::class.java


    override fun initVariable() {
        roomAdapter.addChildClickViewIds(R.id.tv_delete, R.id.tv_user)
        roomAdapter.setOnItemChildClickListener(this)
        roomAdapter.setDiffCallback(object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name == newItem.name
            }

        })

    }

    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Room")


        binding.apply {
            recy.init()

            recy.adapter = roomAdapter


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


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        isAdd = false
        operationItem = roomAdapter.data[position]
        when (view.id) {
            R.id.tv_delete -> {
                vm.deleteUser(operationItem)
            }

            R.id.tv_user -> {
                showInputDialog(title = "修改") { dialog, input ->
                    vm.updateUser(operationItem.id, input.toString())
                }
            }
        }
    }


    override fun observeViewModel() {

        vm.run {
            users.observe(this@RoomActivity) {
                roomAdapter.setDiffNewData(it)
                /*延时滚动到最底部*/
                if (isAdd) {
                    scrollRecy()
                }
            }
        }
    }

    private fun scrollRecy() {
        lifecycleScope.launch {
            delay(200)
            runCatching {
                binding.recy.smoothScrollToPosition(roomAdapter.data.size - 1)
            }
        }
    }


}
