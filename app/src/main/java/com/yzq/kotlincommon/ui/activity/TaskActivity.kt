package com.yzq.kotlincommon.ui.activity


import android.view.Menu
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.task.TaskBean
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.TaskAdapter
import com.yzq.kotlincommon.databinding.ActivityTaskBinding
import com.yzq.lib_base.extend.init
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity
import com.yzq.lib_widget.HoverItemDecoration
import java.util.*


/**
 * @description: 任务页面，主要演示粘性头部
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:41
 *
 */

@Route(path = RoutePath.Main.TASK)
class TaskActivity : BaseViewBindingActivity<ActivityTaskBinding>(), OnItemChildClickListener {


    private var tasks = arrayListOf<TaskBean>()
    private var type: Int = 0
    private lateinit var taskAdapter: TaskAdapter


    override fun getViewBinding() = ActivityTaskBinding.inflate(layoutInflater)


    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.layoutToolbar.toolbar, "任务")
        binding.recy.init()

    }


    override fun initData() {
        super.initData()

        for (i in 1..100) {
            type = if (i % 2 == 0) {
                1
            } else {
                0
            }
            val taskBean = TaskBean("任务${i}", type)
            tasks.add(taskBean)
        }


        /*将数据根据类型分组*/

        filterData()


        showData()


    }

    private fun showData() {


        taskAdapter = TaskAdapter(R.layout.item_task_swipe_layout, tasks)
        taskAdapter.addChildClickViewIds(R.id.tv_name, R.id.tv_delete)
        taskAdapter.setOnItemChildClickListener(this)

        val hoverItemDecoration = HoverItemDecoration(
            this,
            HoverItemDecoration.BindItemTextCallback {
                val taskBean = tasks[it]

                return@BindItemTextCallback if (taskBean.type == 0) {
                    "巡查"
                } else {
                    "急查"
                }

            })
        binding.recy.addItemDecoration(hoverItemDecoration)


        binding.recy.adapter = taskAdapter


    }


    private lateinit var operationItem: TaskBean

    private var operationPosition: Int = 0

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        operationItem = adapter.data[position] as TaskBean
        operationPosition = position
        when (view.id) {
            R.id.tv_name -> {
                ToastUtils.showShort(operationItem.name)
            }
            R.id.tv_delete -> {

                tasks.remove(operationItem)
                taskAdapter.notifyDataSetChanged()

            }
        }
    }


    private fun filterData() {

        tasks.sortBy {
            it.type
        }
//        Collections.sort(tasks, kotlin.Comparator { o1, o2 ->
//            return@Comparator o1.type.compareTo(o2.type)
//
//        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_task, menu)

        return super.onCreateOptionsMenu(menu)
    }


}
