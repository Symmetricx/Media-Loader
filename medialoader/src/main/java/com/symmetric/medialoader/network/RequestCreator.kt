package com.symmetric.medialoader.network

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.symmetric.medialoader.MediaLoader
import com.symmetric.medialoader.cache.LruCache
import com.symmetric.medialoader.cache.Resource
import com.symmetric.medialoader.cache.ResourceType.EMPTY

class RequestCreator(
    private val lruCache: LruCache<String, Resource>,
    uri: Uri,
    private val networkRequestHandler: RequestHandler<String, Resource>
) {

    private var requestBuilder: ResourceRequest.Builder =
        ResourceRequest.Builder(uri)

    /**
     * Asynchronously fulfills the request and invokes the
     * callback [RequestHandler.Callback] if it's not `null`.
     * @param callback request callbacks
     * @return pair of [String] the key for the cache and [Int] the id of the request
     */
    fun listen(callback: RequestHandler.Callback?): Pair<String, Int> {
        this.requestBuilder.callback = callback

        return startRequest()
    }

    /**
     * Asynchronously fulfills the request into the specified [ImageView] and invokes the
     * callback [RequestHandler.Callback] if it's not `null`.
     * @param target [ImageView] the target for fetched [Bitmap] result
     * @param callback request callbacks
     * @return pair of [String] the key for the cache and [Int] the id of the request
     */
    fun into(
        target: ImageView,
        callback: RequestHandler.Callback? = null
    ): Pair<String, Int> {

        this.requestBuilder.target = target
        this.requestBuilder.callback = callback

        return startRequest()

    }

    /**
     * build the request and init it, with a request data [ResourceRequest]
     * @return pair of [String] the key for the cache and [Int] the id of the request
     */
    private fun startRequest(): Pair<String, Int> {
        val request = createRequest()

        val resource = Resource.Builder(lruCache[request.key]).build()

        if (resource.type != EMPTY) {
            networkRequestHandler.handleRequestSuccess(resource, request)
            return request.key to request.id
        }

        networkRequestHandler.load(request)

        return request.key to request.id
    }

    /**
     * build the request [ResourceRequest]
     */
    private fun createRequest(): ResourceRequest {
        return requestBuilder.build()
    }


}