package com.nuvio.tv.core.performance

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DevicePerformanceProfileTest {
    @Test
    fun identifiesAndroidLowRamFlag() {
        assertTrue(
            DevicePerformanceProfile.isLowRamDevice(
                platformLowRamFlag = true,
                totalRamBytes = 4L * GIB,
                maxHeapBytes = 512L * MIB
            )
        )
    }

    @Test
    fun identifiesOlderSmallMemoryTvBoxes() {
        assertTrue(
            DevicePerformanceProfile.isLowRamDevice(
                platformLowRamFlag = false,
                totalRamBytes = 2L * GIB,
                maxHeapBytes = 256L * MIB
            )
        )
    }

    @Test
    fun identifiesConstrainedApplicationHeap() {
        assertTrue(
            DevicePerformanceProfile.isLowRamDevice(
                platformLowRamFlag = false,
                totalRamBytes = 3L * GIB,
                maxHeapBytes = 128L * MIB
            )
        )
    }

    @Test
    fun leavesHealthyDevicesOnFullEffects() {
        assertFalse(
            DevicePerformanceProfile.isLowRamDevice(
                platformLowRamFlag = false,
                totalRamBytes = 4L * GIB,
                maxHeapBytes = 512L * MIB
            )
        )
    }

    private companion object {
        const val MIB = 1024L * 1024L
        const val GIB = 1024L * MIB
    }
}
