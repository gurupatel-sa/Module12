package com.example.module12

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class BaseApplication : Application() {
    var a=0
    override fun onCreate() {
        super.onCreate()
        if(LeakCanary.isInAnalyzerProcess(this))
        {
            return;
        }
        LeakCanary.install(this)
    }
}