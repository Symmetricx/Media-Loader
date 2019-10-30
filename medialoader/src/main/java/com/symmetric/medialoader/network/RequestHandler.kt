package com.symmetric.medialoader.network


interface RequestHandler<K, V> {

    fun getRequests(): Map<String, List<ResourceRequest>>

    /**
     * create a remote call request and fetch needed data
     * on success should call [handleRequestSuccess] to handle the returned [V]
     * and store it in the cache associated with a key [K]
     */
    fun load(request: ResourceRequest)

    /**
     * cancel running or enqueued call request and remove request from requests
     * @param id resource id
     * @param key key for the remote request
     */
    fun cancelRequest(id: Int, key: K)

    /**
     *  handle success call back for retrieved [resource]
     *  @param resource returned value
     *  @param request the request for fetching the resource
     */
    fun handleRequestSuccess(resource: V, request: ResourceRequest)

    /**
     * cancel running or enqueued call request
     * by getting the resource key [K] of a [ResourceRequest] from the requests map
     * @param id resource id
     */
    fun cancelRequest(id: Int)

    interface Callback {
        fun onSuccess(result: Any?)

        fun onError(t: Throwable)
    }
}
