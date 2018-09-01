package com.yzq.kotlincommon.ui


import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yzq.common.ui.BaseActivity
import com.yzq.common.widget.HoverItemDecoration
import com.yzq.common.widget.ItemDecoration
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.TaskAdapter
import com.yzq.kotlincommon.data.TaskBean
import kotlinx.android.synthetic.main.activity_task.*
import java.util.*

class TaskActivity : BaseActivity() {


    var tasks = arrayListOf<TaskBean>()
    private var type: Int = 0
    private lateinit var taskAdapter: TaskAdapter


    override fun getContentLayoutId(): Int {
        return R.layout.activity_task
    }


    override fun initWidget() {
        super.initWidget()


        recy.layoutManager = LinearLayoutManager(this)
        recy.addItemDecoration(ItemDecoration.baseItemDecoration(this))


    }


    override fun initData() {
        super.initData()

        for (i in 1..100) {
            if (i % 2 == 0) {
                type = 1
            } else {
                type = 0
            }
            var taskBean = TaskBean("任务${i}", type)
            tasks.add(taskBean)
        }


        /*将数据根据类型分组*/

        filterData()



        showData()


    }

    private fun showData() {


        taskAdapter = TaskAdapter(R.layout.item_task_layout, tasks)
        recy.addItemDecoration(HoverItemDecoration(this, HoverItemDecoration.BindItemTextCallback {
            var taskBean = tasks[it]
            var title: String
            if (taskBean.type == 0) {
                title = "巡查"
            } else {
                title = "急查"
            }

            return@BindItemTextCallback title


        }))


        recy.adapter = taskAdapter


        showContent()


    }

    private fun filterData() {
        Collections.sort(tasks, kotlin.Comparator { o1, o2 ->
            return@Comparator o1.type.compareTo(o2.type)

        })

    }


    override fun showLoadding() {
        stateView.showLoading()
        recy.visibility = View.GONE
    }

    override fun showContent() {
        recy.visibility = View.VISIBLE
        stateView.hide()
    }
}
