package com.yzq.base.ui.img_pre

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.chrisbanes.photoview.PhotoView

class ImagePagerAdapter(
    private val images: List<String>
) : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val photoView: PhotoView) : RecyclerView.ViewHolder(photoView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val photoView = PhotoView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        return ImageViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imagePath = images[position]
        holder.photoView.load(imagePath)
    }

    override fun getItemCount(): Int = images.size
}