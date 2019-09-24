package com.yzq.kotlincommon.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.RoomAdapter
import com.yzq.kotlincommon.mvvm.view_model.RoomViewModel
import com.yzq.lib_base.extend.init
import com.yzq.lib_base.ui.BaseMvvmActivity
import kotlinx.android.synthetic.main.activity_zoom.*


@Route(path = RoutePath.Main.ROOM)
class RoomActivity : BaseMvvmActivity<RoomViewModel>() {


    private val roomAdapter = RoomAdapter(R.layout.item_room, arrayListOf())

    override fun getContentLayoutId() = R.layout.activity_zoom


    override fun getViewModelClass(): Class<RoomViewModel> = RoomViewModel::class.java


    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Room")

        recy.init()

        recy.adapter = roomAdapter


        fab_add.setOnClickListener {

            vm.insertUser()
        }

    }

    override fun initData() {
        vm.loadData()


    }


    override fun observeViewModel() {

        with(vm) {
            users.observe(this@RoomActivity, Observer {

                roomAdapter.setNewData(it)
            })
        }


    }
}
