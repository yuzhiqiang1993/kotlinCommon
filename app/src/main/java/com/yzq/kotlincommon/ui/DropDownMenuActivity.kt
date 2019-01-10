package com.yzq.kotlincommon.ui

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzq.common.constants.RoutePath
import com.yzq.common.ui.BaseActivity
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.adapter.DropDownMenuFilterAdapter
import com.yzq.kotlincommon.adapter.DropDownMenuFoodTypeAdapter
import kotlinx.android.synthetic.main.activity_drop_down_menu.*


/*
*
* 下拉菜单选择
*
* */

@Route(path = RoutePath.Main.DROP_DOWN_MENU)
class DropDownMenuActivity : BaseActivity(), BaseQuickAdapter.OnItemClickListener {


    override fun getContentLayoutId(): Int {

        return R.layout.activity_drop_down_menu
    }

    private lateinit var filterTv: TextView

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

    @SuppressLint("SetTextI18n")
    override fun initWidget() {


        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        initToolbar(toolbar,"下拉菜单")


        val contentLayout = layoutInflater.inflate(R.layout.drop_down_menu_content, null)
        filterTv = contentLayout.findViewById(R.id.filterTv)


        foodTypeRecy = RecyclerView(this)
        foodTypeRecy.layoutManager = LinearLayoutManager(this)

        filtersRecy = RecyclerView(this)
        filtersRecy.layoutManager = LinearLayoutManager(this)

        popupViews.add(foodTypeRecy)
        popupViews.add(filtersRecy)

        dropdownMenu.setDropDownMenu(tabs, popupViews, contentLayout)

        filterTv.text = "$foodType--$filter"
        setData()
    }

    private fun setData() {

        dropDownMenuFoodTypeAdapter = DropDownMenuFoodTypeAdapter(R.layout.item_drop_down_menu_layout, foodTypes)
        dropDownMenuFoodTypeAdapter.onItemClickListener = this

        dropDownMenuFiltersAdapter = DropDownMenuFilterAdapter(R.layout.item_drop_down_menu_layout, filters)
        dropDownMenuFiltersAdapter.onItemClickListener = this

        foodTypeRecy.adapter = dropDownMenuFoodTypeAdapter
        filtersRecy.adapter = dropDownMenuFiltersAdapter


    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

        when (adapter) {
            is DropDownMenuFoodTypeAdapter -> {

                foodType = dropDownMenuFoodTypeAdapter.data[position]

                dropdownMenu.setTabText(foodType)
                filterTv.text = "$foodType--$filter"

                dropdownMenu.closeMenu()

            }

            is DropDownMenuFilterAdapter -> {
                filter = dropDownMenuFiltersAdapter.data[position]

                dropdownMenu.setTabText(filter)
                filterTv.text = "$foodType--$filter"
                dropdownMenu.closeMenu()
            }
        }


    }


}
