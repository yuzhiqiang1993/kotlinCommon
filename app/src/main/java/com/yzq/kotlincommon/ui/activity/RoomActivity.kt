package com.yzq.kotlincommon.ui.activity

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.data_base.User
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.RoomAdapter
import com.yzq.kotlincommon.mvvm.view_model.RoomViewModel
import com.yzq.lib_base.extend.init
import com.yzq.lib_base.ui.BaseMvvmActivity
import com.yzq.lib_materialdialog.showInputDialog
import kotlinx.android.synthetic.main.activity_zoom.*


@Route(path = RoutePath.Main.ROOM)
class RoomActivity : BaseMvvmActivity<RoomViewModel>(), OnItemChildClickListener {


    private val roomAdapter = RoomAdapter(R.layout.item_room, arrayListOf())

    override fun initContentView() {
        setContentView(R.layout.activity_zoom)

    }

    private lateinit var operationItem: User


    override fun getViewModelClass(): Class<RoomViewModel> = RoomViewModel::class.java


    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Room")

        recy.init()
        roomAdapter.addChildClickViewIds(R.id.tv_delete, R.id.tv_user)
        recy.adapter = roomAdapter
        roomAdapter.setOnItemChildClickListener(this)
        roomAdapter.setDiffCallback(object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id.equals(newItem.id)
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.name.equals(newItem.name)
            }

        })

        fab_add.setOnClickListener {

            vm.insertUser()
        }

    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

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

        with(vm) {
            users.observe(this@RoomActivity, Observer {
                roomAdapter.setDiffNewData(it)

            })


        }


    }


}
