package com.yzq.kotlincommon.ui.activity

import android.view.Menu
import com.blankj.utilcode.util.ToastUtils
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.common.data.task.TaskBean
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityTaskBinding
import com.yzq.kotlincommon.databinding.ItemTaskHoverBinding
import com.yzq.kotlincommon.databinding.ItemTaskSwipeLayoutBinding
import com.yzq.logger.Logger

/**
 * @description 任务列表,主要展示粘性头部和侧滑删除
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2019/4/30
 * @time 13:41
 */

@Route(path = RoutePath.Main.TASK)
class TaskActivity : BaseActivity() {

    private val binding by viewbind(ActivityTaskBinding::inflate)
    private var tasks = arrayListOf<TaskBean>()

    override fun initWidget() {
        super.initWidget()

        initToolbar(binding.includedToolbar.toolbar, "任务")
    }

    override fun initData() {
        super.initData()

        val tasks = mutableListOf<TaskBean>()
        for (i in 1..100) {
            val type = if (i % 2 == 0) {
                "任务类型一"
            } else {
                "任务类型二"
            }
            val taskBean = TaskBean("任务$i", type)
            tasks.add(taskBean)
        }

        /*将数据根据类型分组*/
        filterData(tasks)

        showData()
    }

    private fun showData() {

        binding.recy.linear()
            .divider(R.drawable.item_divider)
            .setup {
                addType<TaskBean> {
                    when (itemHover) {
                        true -> R.layout.item_task_hover
                        else -> R.layout.item_task_swipe_layout
                    }
                }

                onBind {
                    val model = getModel<TaskBean>()
                    when (itemViewType) {
                        R.layout.item_task_swipe_layout -> {
                            getBinding<ItemTaskSwipeLayoutBinding>()
                                .run {
                                    includedTaskContent.tvName.text = model.name
                                }
                        }
                        R.layout.item_task_hover -> {
                            getBinding<ItemTaskHoverBinding>().tvHover.text = model.type
                        }
                    }
                }
                R.id.tv_name.onFastClick {
                    val taskBean = getModel<TaskBean>()
                    Logger.i("点击了:$taskBean")
                    ToastUtils.showShort(taskBean.name)
                }

                R.id.tv_delete.onClick {
                    val taskBean = getModel<TaskBean>()
                    Logger.i("taskBean:$taskBean")
                    val indexOf = tasks.indexOf(taskBean)
                    Logger.i("点击了$taskBean,index:$indexOf")
                    tasks.removeAt(indexOf)
                    notifyItemRemoved(indexOf)
                }
            }.models = tasks
    }

    private fun filterData(taskData: MutableList<TaskBean>) {

        taskData
            .sortedBy { it.type }
            .groupBy {
                it.type
            }.forEach {
                /**
                 * 需要悬停的数据
                 */
                tasks.add(TaskBean(it.key, it.key, true))
                tasks.addAll(it.value)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_task, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
