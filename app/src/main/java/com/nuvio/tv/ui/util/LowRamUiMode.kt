package com.nuvio.tv.ui.util

import androidx.compose.runtime.staticCompositionLocalOf

/** True on memory-constrained TV boxes; expensive visual effects should be avoided when set. */
val LocalLowRamUiMode = staticCompositionLocalOf { false }
