package com.maxab.baseproject

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.Relay
import com.jakewharton.rxrelay2.ReplayRelay
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

open class BaseViewModel : ViewModel() {

    private val loading = ReplayRelay.create<Boolean>()

    fun loadingOn(): Consumer<Disposable> {
        return Consumer { loading.accept(true) }
    }

    fun loadingOff(): Consumer<Any> {
        return Consumer { loading.accept(false) }
    }

    fun loading(): Relay<Boolean>{
        return loading
    }
}