package com.yzq.kotlincommon.ui.activity

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.DropDownMenuFilterAdapter
import com.yzq.kotlincommon.adapter.DropDownMenuFoodTypeAdapter
import com.yzq.kotlincommon.databinding.ActivityDropDownMenuBinding
import com.yzq.lib_base.ui.activity.BaseViewBindingActivity
import kotlinx.android.synthetic.main.activity_drop_down_menu.*


/**
 * @description: 下拉菜单
 * @author : yzq
 * @date   : 2019/4/30
 * @time   : 13:38
 *
 */

@Route(path = RoutePath.Main.DROP_DOWN_MENU)
class DropDownMenuActivity : BaseViewBindingActivity<ActivityDropDownMenuBinding>(),
    OnItemClickListener {


    private lateinit var tvFilter: AppCompatTextView

    private lateinit var dropDownMenuFoodTypeAdapter: DropDownMenuFoodTypeAdapter
    private lateinit var dropDownMenuFiltersAdapter: DropDownMenuFilterAdapter

    private var foodType: String = "全部美食"
    private var filter: String = "智能排序"


    private val foodTypes = arrayListOf("全部美食", "福建菜", "江浙菜", "川菜")
    private val filters = arrayListOf("智能排序", "离我最近", "好评优先", "人气最高")

    private val tabs = arrayListOf("全部美食", "智能排序")
    private val popupViews = arrayListOf<View>()


    private lateinit var foodTypeRecy: RecyclerView
    private lateinit var filtersRecy: RecyclerView

    override fun getViewBinding() = ActivityDropDownMenuBinding.inflate(layoutInflater)


    @SuppressLint("SetTextI18n")
    override fun initWidget() {


        initToolbar(binding.layoutToolbar.toolbar, "下拉菜单")


        val contentLayout = layoutInflater.inflate(R.layout.drop_down_menu_content, null)
        tvFilter = contentLayout.findViewById(R.id.tv_filter)


        foodTypeRecy = RecyclerView(this)
        foodTypeRecy.layoutManager = LinearLayoutManager(this)

        filtersRecy = RecyclerView(this)
        filtersRecy.layoutManager = LinearLayoutManager(this)

        popupViews.add(foodTypeRecy)
        popupViews.add(filtersRecy)

        dropdown_menu.setDropDownMenu(tabs, popupViews, contentLayout)

        tvFilter.text = "$foodType--$filter"
        setData()
    }

    private fun setData() {

        dropDownMenuFoodTypeAdapter =
            DropDownMenuFoodTypeAdapter(R.layout.item_drop_down_menu_layout, foodTypes)
        dropDownMenuFoodTypeAdapter.setOnItemClickListener(this)

        dropDownMenuFiltersAdapter =
            DropDownMenuFilterAdapter(R.layout.item_drop_down_menu_layout, filters)
        dropDownMenuFiltersAdapter.setOnItemClickListener(this)

        foodTypeRecy.adapter = dropDownMenuFoodTypeAdapter
        filtersRecy.adapter = dropDownMenuFiltersAdapter


    }


    @SuppressLint("SetTextI18n")
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        when (adapter) {
            is DropDownMenuFoodTypeAdapter -> {

                foodType = dropDownMenuFoodTypeAdapter.data[position]

                dropdown_menu.setTabText(foodType)
                tvFilter.text = "$foodType--$filter"

                dropdown_menu.closeMenu()

            }

            is DropDownMenuFilterAdapter -> {
                filter = dropDownMenuFiltersAdapter.data[position]

                dropdown_menu.setTabText(filter)
                tvFilter.text = "$foodType--$filter"
                dropdown_menu.closeMenu()
            }
        }


    }


}
