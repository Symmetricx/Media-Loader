package com.symmetric.medialoader.network

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import com.symmetric.medialoader.cache.LruCache
import com.symmetric.medialoader.cache.Resource
import com.symmetric.medialoader.cache.ResourceType.BITMAP
import com.symmetric.medialoader.cache.ResourceType.BYTE_ARRAY
import com.symmetric.medialoader.util.decodeSampledBitmap
import com.symmetric.medialoader.util.getScreenDim
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.collections.HashMap

class NetworkRequestHandler(
    private val lruCache: LruCache<String, Resource>,
    private val okHttpClient: OkHttpClient,
    private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
) : RequestHandler<String, Resource> {
    override val requests: HashMap<String, MutableList<ResourceRequest>> = HashMap()


    override fun load(request: ResourceRequest) {

        addRequest(request)

        /**
         * if request for the same key already exists skip creating a new call
         **/
        if (requests[request.key] != null && requests[request.key]!!.count() > 1) return

        val callRequest = createRequest(request)
        okHttpClient.newCall(callRequest).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    handleFailure(
                        request,
                        ResponseException(
                            response.code,
                            "Not successful"
                        )
                    )
                    return
                }

                val body = response.body
                if (body == null || body.contentLength() == 0L) {
                    handleFailure(
                        request,
                        ResponseException(
                            response.code,
                            "Received response with 0 content-length header."
                        )
                    )
                    return
                }

                try {
                    val (width, height) = getScreenDim()
                    if (request.isImage) {
                        val bitmap: Bitmap = decodeSampledBitmap(
                            body.bytes(),
                            width,
                            height
                        )
                        handleSuccess(Resource.Builder(bitmap).build(), request)
                    } else {
                        handleSuccess(Resource.Builder(body.bytes()).build(), request)
                    }
                } catch (e: IOException) {
                    body.close()
                    handleFailure(request, e)
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                handleFailure(request, e)
            }
        })
    }

    /**
     * add request to [requests] map
     * @param [request]
     */
    private fun addRequest(request: ResourceRequest) {
        val key = request.key
        if (requests[key] == null) {
            requests[key] = listOf(request).toMutableList()
        } else {
            requests[key]!!.add(request)
        }
    }

    /**
     * build a [Request] from the [request]
     * @param [request]
     * @return [Request]
     */
    private fun createRequest(request: ResourceRequest): Request {
        val uri = checkNotNull(request.uri) { "request.uri == null" }

        return Request.Builder()
            .url(uri.toString())
            .tag(request.key)
            .build()

    }

    /**
     * cancel a request and stop if there is an ongoing request for this call
     * @param [id] resource id
     * @param [key] request key in [requests] map
     */
    override fun cancelRequest(id: Int, key: String) {
        if (requests[key] == null) return

        requests[key]?.removeAll { it.id == id }
        if (requests[key]?.count() == 0) {
            stopNetworkCall(key)
            requests.remove(key)
        }
    }


    private fun handleSuccess(resource: Resource, request: ResourceRequest) {
        requests[request.key]?.forEach {
            handleRequestSuccess(resource, it)
        }
        lruCache[request.key] = resource

        requests.remove(request.key)
    }

    override fun handleRequestSuccess(resource: Resource, it: ResourceRequest) {
        when {
            resource.type == BYTE_ARRAY -> {
                mainThreadHandler.post {
                    it.callback?.onSuccess(resource.byteArray)
                }
            }
            resource.type == BITMAP -> {
                mainThreadHandler.post {
                    it.callback?.onSuccess(resource.bitmap)
                    if (it.isImage && it.target.get() != null) {
                        it.target.get()?.setImageBitmap(resource.bitmap)
                    }
                }
            }
        }

    }

    override fun cancelRequest(id: Int) {
        val key = getKey(id) ?: return

        cancelRequest(id, key)
    }

    /**
     * check if [tag] has a an on-going request or queued request
     * @param [tag] the request tag associated with request on [createRequest]
     */
    private fun stopNetworkCall(tag: String) {

        okHttpClient.dispatcher.queuedCalls().forEach {
            if (it.request().tag() == tag) {
                it.cancel()
            }
        }

        okHttpClient.dispatcher.runningCalls().forEach {
            if (it.request().tag() == tag) {
                it.cancel()
            }
        }
    }

    /**
     * handle [Request] failure
     * @param [request] the failed resource request
     * @param [exception] the returned exception
     */
    private fun handleFailure(request: ResourceRequest, exception: Exception) {
        request.callback?.onError(exception)
        cancelRequest(request.id, request.key)
    }

    /**
     * get [Request] key using an [id] from the [requests] map
     * @param [id] [ResourceRequest.id]
     */
    private fun getKey(id: Int): String? {

        requests.forEach { entry ->
            entry.value.forEach {
                if (it.id == id) {
                    return entry.key
                }
            }
        }
        return null
    }

    class ResponseException(code: Int, override val message: String) :
        RuntimeException("HTTP $code: $message")
}
