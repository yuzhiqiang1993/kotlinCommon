package com.yzq.lib_rx

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/*
* 只关心OnNext的Observer
* */
abstract class NextObserver<T>:Observer<T>{

    override fun onSubscribe(d: Disposable) {

    }


    override fun onComplete() {

    }

    override fun onError(e: Throwable) {


    }


}