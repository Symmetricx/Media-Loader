package com.symmetric.medialoader.cache

import android.util.LruCache


/** A memory cache which uses a least-recently used eviction policy.  */
interface LruCache<K,V> {

    /**
     * an instance of [LruCache] that will be used to cache resources
     */
    val cache: LruCache<K, V>

    /**
     * get a [V] value using a [K] key from the [cache]
     * @param key key in the [cache]
     * @return [V] if value exist return [V] else return null
     */
    operator fun get(key: K): V?

    /**
     * set a [V] value and associate with [K] key in [cache]
     * @param key
     * @param resource
     */
    operator fun set(key: K, resource: V)

    /**
     * @return [Int] size of the cache
     */
    fun size(): Int

    /**
     * @return [Int] max size of the cache
     */
    fun maxSize(): Int

    /**
     * clear all data in [cache]
     */
    fun clear()

    /**
     * remove a [V] value using a key [K]
     * @param key the key of value to remove
     */
    fun clearKey(key: K)
}
