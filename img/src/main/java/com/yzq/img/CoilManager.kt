package com.yzq.img

import android.os.Build.VERSION.SDK_INT
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.yzq.application.AppContext

object CoilManager {
    //创建 gif ImageLoader 实例
//    val imageLoader = ImageLoader.Builder(AppContext)
//        .components(fun ComponentRegistry.Builder.() {
//            add { result, options, imageLoader ->
//                when (result.mimeType) {
//                    "image/gif" -> GifDecoder(result.source, options)
//                    else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                        ImageDecoderDecoder(result.source, options)
//                    } else {
//                        GifDecoder(result.source, options)
//                    }
//                }
//            }
//        }).build()


    private val imageLoader = ImageLoader.Builder(AppContext)
        .crossfade(true)
        .memoryCache {
            MemoryCache.Builder(AppContext)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(AppContext.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.1)
                .build()
        }
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
            add(SvgDecoder.Factory())
        }
        .build()

    fun init(imageLoader: ImageLoader? = null) {
        Coil.setImageLoader(imageLoader ?: this.imageLoader)
    }
}