package com.yzq.kotlincommon.ui.activity

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.therouter.router.Route
import com.yzq.base.extend.initToolbar
import com.yzq.base.ui.activity.BaseActivity
import com.yzq.binding.viewbind
import com.yzq.common.constants.RoutePath
import com.yzq.kotlincommon.R
import com.yzq.kotlincommon.databinding.ActivityDropDownMenuBinding
import com.yzq.kotlincommon.databinding.DropDownMenuContentBinding
import com.yzq.kotlincommon.databinding.ItemDropDownMenuLayoutBinding

/**
 * @description: 下拉菜单
 * @author : yzq
 * @date : 2019/4/30
 * @time : 13:38
 *
 */

@Route(path = RoutePath.Main.DROP_DOWN_MENU)
class DropDownMenuActivity : BaseActivity() {

    private val binding by viewbind(ActivityDropDownMenuBinding::inflate)

    private lateinit var tvFilter: AppCompatTextView

    private val foodTypes = arrayListOf("全部美食", "福建菜", "江浙菜", "川菜")
    private val filters = arrayListOf("智能排序", "离我最近", "好评优先", "人气最高")

    private lateinit var foodTypeRecy: RecyclerView
    private lateinit var filtersRecy: RecyclerView

    override fun initWidget() {

        initToolbar(binding.includedToolbar.toolbar, "下拉菜单")

        val contentLayout = View.inflate(this, R.layout.drop_down_menu_content, null)

//        tvFilter = contentLayout.findViewById(R.id.tv_filter)

        val dropDownMenuContentBinding = DropDownMenuContentBinding.bind(contentLayout)
        dropDownMenuContentBinding.tvFilter.text = "底部内容区域"

        foodTypeRecy = RecyclerView(this).linear()

        filtersRecy = RecyclerView(this).linear()

        /*顶部tab以及对应的内容view*/
        val map = mutableMapOf<String, View>().apply {
            put("全部美食", foodTypeRecy)
            put("智能排序", filtersRecy)
        }

        binding.dropdownMenu.setDropDownMenu(map, contentLayout)

        setData()
    }

    private fun setData() {

        val block: BindingAdapter.(RecyclerView) -> Unit = {
            addType<String>(R.layout.item_drop_down_menu_layout)
            onBind {
                val itemBinding = getBinding<ItemDropDownMenuLayoutBinding>()
                itemBinding.tvContent.setText(getModel<String>())
            }
            R.id.tv_content.onClick {
                binding.dropdownMenu.setTabText(getModel())
                binding.dropdownMenu.closeMenu()
            }
        }

        filtersRecy.setup(block).models = filters

        foodTypeRecy.setup(block).models = foodTypes
    }
}
