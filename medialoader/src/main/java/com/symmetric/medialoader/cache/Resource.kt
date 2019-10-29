package com.symmetric.medialoader.cache

import android.graphics.Bitmap
import androidx.core.graphics.BitmapCompat
import com.symmetric.medialoader.cache.ResourceType.BITMAP
import com.symmetric.medialoader.cache.ResourceType.EMPTY
import com.symmetric.medialoader.cache.ResourceType.BYTE_ARRAY

data class Resource private constructor(
    val byteArray: ByteArray? = null,
    val bitmap: Bitmap? = null,

    /**
     *
     * Will hold a value indicating the type of the resource the current available values
     * and their indications:
     * [EMPTY] -> Empty resource,
     * [BITMAP] -> Bitmap resource,
     * [BYTE_ARRAY] -> Byte Array resource,
     */
    val type: Int = 0,

    var byteCount: Int = 0
) {

    init {
        calcSize()
    }

    private fun calcSize(): Int {
        this.byteCount = when (this.type) {
            EMPTY -> 0
            BITMAP -> return if (this.bitmap != null) {
                BitmapCompat.getAllocationByteCount(this.bitmap)
            } else 0

            BYTE_ARRAY -> return if (this.byteArray != null) {
                this.byteArray.size
            } else 0
            else -> 0
        }
        return byteCount
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Resource

        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (bitmap != other.bitmap) return false
        if (byteCount != other.byteCount) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray?.contentHashCode() ?: 0
        result = 31 * result + (bitmap?.hashCode() ?: 0)
        result = 31 * result + byteCount
        result = 31 * result + type
        return result
    }

    class Builder() {
        private var type: Int = 0
        private var bitmap: Bitmap? = null
        private var byteArray: ByteArray? = null

        public constructor(resource: Resource?) : this() {
            this.bitmap = resource?.bitmap
            this.byteArray = resource?.byteArray

        }

        public constructor(byteArray: ByteArray?) : this() {
            this.byteArray = byteArray
        }

        public constructor(bitmap: Bitmap) : this() {
            this.bitmap = bitmap
        }

        public fun build(): Resource {
            this.type = when {
                this.byteArray != null -> BYTE_ARRAY
                this.bitmap != null -> BITMAP
                else -> EMPTY
            }
            return Resource(this.byteArray, this.bitmap, this.type, 0)
        }

    }
}

