package com.symmetric.medialoader

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.util.Patterns
import com.symmetric.medialoader.cache.LruCache
import com.symmetric.medialoader.cache.Resource
import com.symmetric.medialoader.cache.ResourceLruCache
import com.symmetric.medialoader.network.NetworkRequestHandler
import com.symmetric.medialoader.network.RequestCreator
import com.symmetric.medialoader.network.RequestHandler
import com.symmetric.medialoader.network.ResourceRequest
import com.symmetric.medialoader.util.calculateMemoryCacheSize
import okhttp3.OkHttpClient
import okhttp3.Request

class MediaLoader(
    private val networkRequestHandler: RequestHandler<String, Resource>,
    internal val lruCache: LruCache<String, Resource>
) {

    /**
     * Start a request for
     * @param uri with remote valid url
     * @return [RequestCreator] a request creator
     */
    fun load(uri: Uri): RequestCreator {
        return RequestCreator(lruCache, uri, networkRequestHandler)
    }

    /**
     * Start an resource request using the specified path. This is a convenience method for calling
     * [load].
     *
     * @param path valid remote url
     * @return [RequestCreator] a request creator
     */
    fun load(path: String): RequestCreator {
        require(Patterns.WEB_URL.matcher(path).matches()) { "Path must not be valid URL." }
        return load(Uri.parse(path))
    }

    /**
     * cancel a request associated with a request id [ResourceRequest.id]
     * @param id the request id [ResourceRequest.id]
     */
    fun cancelRequest(id: Int) {
        networkRequestHandler.cancelRequest(id)
    }

    class Builder(val context: Context) {
        var cache: LruCache<String, Resource>? = null
        var okHttpClient: OkHttpClient? = null
        var handler: Handler? = null
        var networkRequestHandler: NetworkRequestHandler? = null
        var lruCache: android.util.LruCache<String, Resource>? = null
        var requestBuilder: Request.Builder? = null

        fun build(): MediaLoader {

            if (this.networkRequestHandler != null && this.cache != null) {
                return MediaLoader(
                    this.networkRequestHandler!!,
                    this.cache!!
                )
            }

            if (this.cache == null) {

                if (lruCache == null) {
                    // Init an LruCache
                    val maxSize = calculateMemoryCacheSize(this.context)

                    lruCache = object : android.util.LruCache<String, Resource>(maxSize) {
                        override fun sizeOf(key: String, value: Resource): Int {
                            return value.byteCount
                        }
                    }
                }

                cache = ResourceLruCache(lruCache!!)
            }

            if (this.okHttpClient == null) {
                okHttpClient = OkHttpClient()
            }

            if (this.handler == null) {
                handler = Handler(context.mainLooper)
            }

            if(requestBuilder == null){
                requestBuilder = Request.Builder()
            }

            if (this.networkRequestHandler == null) {

                this.networkRequestHandler = NetworkRequestHandler(
                    this.cache!!,
                    this.okHttpClient!!,
                    this.handler!!,
                    this.requestBuilder!!
                )
            }


            return MediaLoader(
                this.networkRequestHandler!!,
                this.cache!!
            )
        }
    }

    companion object {

        @Volatile
        private var instance: MediaLoader? = null

        fun get(context: Context?): MediaLoader {
            require(context != null) { "Context must not be null" }

            return instance?: synchronized(this){
                instance?:Builder(context).build().also { instance = it }
            }
        }
    }

}