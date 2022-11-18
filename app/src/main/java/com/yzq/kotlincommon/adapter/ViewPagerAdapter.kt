package com.yzq.kotlincommon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yzq.kotlincommon.databinding.ItemViewPagerBinding

/**
 * @description ViewPager的adapter,也就是原生Adapter的写法
 * @author yuzhiqiang (zhiqiang.yu.xeon@gmail.com)
 * @date 2022/11/18
 * @time 13:58
 */

class ViewPagerAdapter(val data: List<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvPagerContent.text = item
        }
    }

    /**
     * 创建viewholder,创建次数为屏幕可显示的item条数
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewPagerBinding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemViewPagerBinding)
    }

    /**
     * 绑定viewholder 每次item显示到屏幕上就会被回调
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
