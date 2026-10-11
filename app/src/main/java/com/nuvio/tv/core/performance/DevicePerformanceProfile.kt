package com.nuvio.tv.core.performance

import android.app.ActivityManager
import android.content.Context

/** Lightweight device-tier check shared by image loading and the TV UI. */
object DevicePerformanceProfile {
    private const val LOW_RAM_TOTAL_BYTES = 2L * 1024L * 1024L * 1024L
    private const val LOW_APP_HEAP_BYTES = 192L * 1024L * 1024L

    fun isLowRamDevice(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        val lowRamFlag = runCatching { activityManager?.isLowRamDevice == true }.getOrDefault(false)
        val totalRamBytes = runCatching {
            ActivityManager.MemoryInfo().also { activityManager?.getMemoryInfo(it) }.totalMem
        }.getOrDefault(0L)
        return isLowRamDevice(
            platformLowRamFlag = lowRamFlag,
            totalRamBytes = totalRamBytes,
            maxHeapBytes = Runtime.getRuntime().maxMemory()
        )
    }

    internal fun isLowRamDevice(
        platformLowRamFlag: Boolean,
        totalRamBytes: Long,
        maxHeapBytes: Long
    ): Boolean = platformLowRamFlag ||
        (totalRamBytes > 0L && totalRamBytes <= LOW_RAM_TOTAL_BYTES) ||
        maxHeapBytes <= LOW_APP_HEAP_BYTES
}
