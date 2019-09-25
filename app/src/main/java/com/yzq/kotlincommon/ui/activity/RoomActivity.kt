package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.diff.BaseQuickDiffCallback
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.RoomAdapter
import com.yzq.kotlincommon.data.data_base.User
import com.yzq.kotlincommon.mvvm.view_model.RoomViewModel
import com.yzq.lib_base.extend.init
import com.yzq.lib_base.ui.BaseMvvmActivity
import com.yzq.lib_materialdialog.showInputDialog
import com.yzq.lib_materialdialog.showOnlyPostiveCallBackDialog
import kotlinx.android.synthetic.main.activity_zoom.*


@Route(path = RoutePath.Main.ROOM)
class RoomActivity : BaseMvvmActivity<RoomViewModel>(), BaseQuickAdapter.OnItemClickListener,
    BaseQuickAdapter.OnItemLongClickListener {


    private val roomAdapter = RoomAdapter(R.layout.item_room, arrayListOf())

    override fun getContentLayoutId() = R.layout.activity_zoom


    override fun getViewModelClass(): Class<RoomViewModel> = RoomViewModel::class.java


    override fun initWidget() {

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar, "Room")

        recy.init()

        recy.adapter = roomAdapter
        roomAdapter.onItemClickListener = this
        roomAdapter.onItemLongClickListener = this


        fab_add.setOnClickListener {

            vm.insertUser()
        }

    }

    override fun initData() {
        vm.loadData()


    }

    @SuppressLint("AutoDispose", "CheckResult")
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val clickUser = roomAdapter.data[position]
        showInputDialog()
            .subscribe { input ->

                clickUser.name = input


                vm.updateUser(clickUser, position)

            }

    }


    @SuppressLint("AutoDispose", "CheckResult")
    override fun onItemLongClick(
        adapter: BaseQuickAdapter<*, *>?,
        view: View?,
        position: Int
    ): Boolean {
        val clickUser = roomAdapter.data[position]
        showOnlyPostiveCallBackDialog(message = "确认删除${clickUser.name}")
            .subscribe { clickPositive ->
                vm.deleteUser(clickUser)

            }

        return true
    }


    override fun observeViewModel() {

        with(vm) {
            users.observe(this@RoomActivity, Observer {


                LogUtils.i("旧数据：${roomAdapter.data}")

                LogUtils.i("数据发生变化:${it}")

                val newDatas = arrayListOf<User>()
                newDatas.addAll(it)

                roomAdapter.setNewDiffData(object : BaseQuickDiffCallback<User>(newDatas) {

                    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {

                        return oldItem.id.equals(newItem.id)
                    }

                    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {

                        return oldItem.name.equals(newItem.name)
                    }
                })
            })


            updatePosition.observe(this@RoomActivity, Observer {

                roomAdapter.notifyItemChanged(it)
            })
        }


    }


}
