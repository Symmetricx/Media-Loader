package com.symmetric.medialoader.cache

import android.util.LruCache
import com.symmetric.medialoader.cache.ResourceType.BITMAP
import com.symmetric.medialoader.cache.ResourceType.BYTE_ARRAY
import com.symmetric.medialoader.cache.ResourceType.EMPTY


/** A memory cache which uses a least-recently used eviction policy.  */
class ResourceLruCache
/** Create a cache with a given maximum size in bytes.  */
    (maxByteCount: Int) : com.symmetric.medialoader.cache.LruCache<String, Resource>{

    override val cache: LruCache<String, Resource> =
        object : LruCache<String, Resource>(if (maxByteCount != 0) maxByteCount else 1) {
            override fun sizeOf(key: String, value: Resource): Int {
                return value.byteCount
            }
        }


    override operator fun get(key: String): Resource? {
        return cache.get(key)
    }

    override operator fun set(key: String, resource: Resource) {

        var notValidResource = false


        when (resource.type) {
            EMPTY -> notValidResource = true
            BITMAP -> if (resource.bitmap == null) notValidResource = true
            BYTE_ARRAY -> if (resource.byteArray == null) notValidResource = true
        }

        /** If the resource is too big for the cache, don't even attempt to store it. Doing so will cause
         * the cache to be cleared. Instead just evict an existing element with the same key if it
         * exists.
         **/
        if (resource.byteCount != 0 && resource.byteCount > maxSize() || notValidResource) {
            cache.remove(key)
            return
        }

        cache.put(key, resource)
    }

    override fun size(): Int {
        return cache.size()
    }

    override fun maxSize(): Int {
        return cache.maxSize()
    }

    override fun clear() {
        cache.evictAll()
    }

    override fun clearKey(key: String) {

        cache.snapshot().forEach {
            if (it.key.startsWith(key)) {
                cache.remove(it.key)
            }
        }
    }
}
