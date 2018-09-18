package com.example.kotlincoroutinesplayground

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.experimental.Job

class CoroutineLifecycleObserver : LifecycleObserver {
    var job: Job = Job()
        private set

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        // If the job is complete (happens after being previously stopped)
        // lets create a new one
        if (job.isCompleted) {
            job = Job()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun cancel() {
        // If the job is active (running) cancel it
        if (job.isActive) {
            job.cancel()
        }
    }
}