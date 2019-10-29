package com.symmetric.medialoader.util

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP
import androidx.core.content.ContextCompat

fun calculateMemoryCacheSize(context: Context): Int {
    val am = ContextCompat.getSystemService(context, ActivityManager::class.java)
    val largeHeap = context.applicationInfo.flags and FLAG_LARGE_HEAP != 0
    val memoryClass = if (largeHeap) am!!.largeMemoryClass else am!!.memoryClass
    // Target ~15% of the available heap.
    return (1024L * 1024L * memoryClass.toLong() / 7).toInt()
}


/**
 * the current dims are stub dimension of the screen
 */
fun getScreenDim(): Pair<Int, Int>{
    return 1000 to 1000
}

