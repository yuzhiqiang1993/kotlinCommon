package com.yzq.lib_rx

import io.reactivex.Observer


abstract class NextObserver<T>:Observer<T>{


    override fun onComplete() {

    }

    override fun onError(e: Throwable) {


    }


}