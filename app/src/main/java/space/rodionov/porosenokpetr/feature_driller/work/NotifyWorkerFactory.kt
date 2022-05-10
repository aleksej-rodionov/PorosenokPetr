package space.rodionov.porosenokpetr.feature_driller.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

/**
 * If there is no worker found, return null to use the default behaviour of [WorkManager]
 * (create worker using refection)
 *
 * In addition you can use dagger multi-binding to avoid manual check the workerClassName
 * but if you not familiar dagger multi-binding then it better to do it manually since
 * it easier to understand.
 *
 * Check out earlier commit to see the dagger multi-binding solution!
 *
 * @see WorkerFactory.createWorkerWithDefaultFallback
 */
class NotifyWorkerFactory @Inject constructor(
    private val notifyWorkerFactory: NotificationWorker.Factory,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        return when (workerClassName) {
            NotificationWorker::class.java.name ->
                notifyWorkerFactory.create(appContext, workerParameters)
            else -> null
        }
    }
}