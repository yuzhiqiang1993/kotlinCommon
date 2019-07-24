package com.yzq.common.extend

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import com.yzq.common.data.BaseResp
import com.yzq.common.data.ResponseCode
import com.yzq.common.rx.RxSchedulers
import io.reactivex.Observable
import io.reactivex.Single


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


fun <T> Single<T>.transform(owner: LifecycleOwner): SingleSubscribeProxy<T> {

    return this.compose(RxSchedulers.io2mainForSingle()).autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY))
}

/*
* 数据转换
*
* */
fun <T> Single<BaseResp<T>>.dataConvert(): Single<T> {
    return flatMap { baseResp ->
        if (baseResp.errorCode == ResponseCode.SUCCESS) Single.just(baseResp.result) else Single.error(Throwable(message = baseResp.reason))
    }
}
