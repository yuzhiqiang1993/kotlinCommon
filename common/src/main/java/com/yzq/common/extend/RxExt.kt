package com.yzq.common.extend

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import com.yzq.common.data.BaseResp
import com.yzq.common.data.ResponseCode
import com.yzq.common.rx.RxSchedulers
import io.reactivex.Observable
import io.reactivex.functions.Function


/**
 * @description: 对RxKotlin的扩展
 * @author : yzq
 * @date   : 2018/7/9
 * @time   : 15:36
 *
 */

/*
* 对Observable进行线程调度和生命周期绑定
*
* */
fun <T> Observable<T>.transform(owner: LifecycleOwner): ObservableSubscribeProxy<T> {
    return this.compose(RxSchedulers.io2main()).autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY))
}


/*
* 数据转换
*
* */
fun <T> Observable<BaseResp<T>>.dataConvert(): Observable<T> {

    return this.flatMap(object : Function<BaseResp<T>, Observable<T>> {
        override fun apply(t: BaseResp<T>): Observable<T> {

            if (t.errorCode == ResponseCode.SUCCESS) {

                return Observable.just(t.result)
            }
            return Observable.error(Throwable(message = t.reason))
        }

    })


}


