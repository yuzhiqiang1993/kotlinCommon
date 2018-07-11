package com.yzq.common.mvp.view


/**
 * @description: BaseView基类
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 13:23
 *
 */

interface BaseView {

     fun showLoadingDialog(content: String="请求中...")

     fun dismissLoadingDialog()

     fun showProgressDialog(title: String, content: String)

     fun dismissProgressDialog()

     fun changeProgress(percent: Int)


     fun showErrorDialog(msg: String)

     fun showLoadding()

     fun showContent()

     fun showNoData()

     fun showNoNet()

     fun showError(msg: String)

}