package com.yzq.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.therouter.TheRouter
import com.yzq.dialog.BubbleLoadingDialog
import com.yzq.logger.Logger

/**
 * @description: fragment基类
 * @author : yzq
 * @date : 2018/7/11
 * @time : 9:49
 *
 */
abstract class BaseFragment(@LayoutRes private val contentLayoutId: Int = 0) :
    Fragment(contentLayoutId) {


    protected val TAG = "${this.javaClass.simpleName}-${this.hashCode()}"
    protected val loadingDialog by lazy { BubbleLoadingDialog(requireActivity()) }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        initArgs(arguments)
    }


//    init {
//        Logger.it(TAG, "init start")
//        /**
//         * viewLifecycleOwnerLiveData 是一个LifecycleOwner，当Fragment的视图创建完成时，会触发其onStateChanged回调
//         *
//         */
//        viewLifecycleOwnerLiveData.observe(this) { viewLifecycleOwner ->
//
//            /**
//             * 当Fragment的onCreteView执行完毕后，这里就会被回调了，因为viewLifecycleOwner有值了。
//             */
//            Logger.it(
//                TAG,
//                "init viewLifecycleOwnerLiveData 监听 viewLifecycleOwner 时，viewLifecycleOwner的值: ${viewLifecycleOwner}"
//            )
//            /**
//             * viewLifecycleOwner 监听的是Fragment中View的生命周期。生命周期范围是 onCreateView->onDestroyView
//             */
//            viewLifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
//                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//                    Logger.it(TAG, "Feagment中View的生命周期 onStateChanged: ${event}")
//                }
//            })
//        }
//
//        /**
//         * 监听Fragment本身的生命周期，范围是onCreate->onDestroy
//         */
//        this.lifecycle.addObserver(object : LifecycleEventObserver {
//            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//                Logger.it(TAG, "Feagment本身的生命周期 onStateChanged：:${event}")
//            }
//
//        })
//
//        Logger.it(TAG, "init end")
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.it(TAG, "onCreate start")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TheRouter.inject(this)
        //初始化数据
        initVariable()
        //初始化view
        initWidget()
        //viewmodel的监听
        observeViewModel()
        initData()
    }


    protected open fun initVariable() {}

    /**
     * Init args
     *
     * @param arguments
     */
    protected open fun initArgs(arguments: Bundle?) {
    }

    /**
     * 初始化数据
     */
    protected open fun initData() {
    }

    /**
     * 初始化View
     */
    protected open fun initWidget() {}

    /**
     * 监听vm中的数据
     */
    protected open fun observeViewModel() {}

    /**
     * 返回键监听
     * @return Boolean
     */
    open fun onBackPressed(): Boolean {/*如果子类重写返回true  表示fragment要拦截返回键的逻辑 自己做处理*/
        return false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        Logger.it(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.it(TAG, "onDestroy")
    }
}
