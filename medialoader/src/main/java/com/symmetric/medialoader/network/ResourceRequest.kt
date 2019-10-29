package com.symmetric.medialoader.network

import android.net.Uri
import android.widget.ImageView
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicInteger

class ResourceRequest private constructor(

    /**
     * unique request id
     */
    val id: Int,
    val uri: Uri,

    val target: WeakReference<ImageView?>,
    /**
     * the image target width and height
     */
    val targetWidth: Int = 0,
    val targetHeight: Int = 0,
    val isImage: Boolean = false,

    val callback: RequestHandler.Callback? = null,
    val key: String
    ) {


    public fun hasSize(): Boolean {
        return targetHeight != 0 || targetWidth != 0
    }


    class Builder(
        var uri: Uri
    ) {
        var id: Int = 0
        var targetWidth: Int = 0
        var targetHeight: Int = 0
        var isImage: Boolean = false
        var callback: RequestHandler.Callback? = null
        var target: ImageView? = null

        constructor(uri: Uri, targetWidth: Int, targetHeight: Int) : this(uri) {
            this.targetHeight = targetHeight
            this.targetWidth = targetWidth
        }


        constructor(uri: Uri, targetWidth: Int, targetHeight: Int, isImage: Boolean) : this(
            uri,
            targetWidth,
            targetHeight
        ) {
            this.isImage = isImage
        }

        private fun generateKey() : String{
            return "$uri\n$isImage"
        }

        fun build(): ResourceRequest {
            this.id = nextId.getAndIncrement()

            if (this.target != null) {
                this.isImage = true
            }


            return ResourceRequest(
                this.id,
                this.uri,
                WeakReference(this.target),
                this.targetWidth,
                this.targetHeight,
                this.isImage,
                this.callback,
                generateKey()
            )
        }

        companion object {
            private val nextId = AtomicInteger()
        }

    }
}

