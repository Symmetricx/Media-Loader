package com.symmetric.medialoader.cache

import android.util.LruCache
import com.symmetric.medialoader.generateByteArray
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResourceLruCacheTest {

    @Mock
    lateinit var lruCache: LruCache<String, Resource>

    lateinit var bigResource: Resource

    lateinit var smallResource: Resource

    lateinit var cache: ResourceLruCache



    //  4 MB
    private val four_mb = 4 * 1024 * 1024

    private val key = "KEY"

    @Before
    fun setUp() {
        cache = ResourceLruCache(lruCache)

        smallResource = Resource
            .Builder(generateByteArray())
            .build()

        bigResource = Resource
            .Builder(generateByteArray(four_mb + 1))
            .build()

        `when`(lruCache.maxSize()).thenReturn(four_mb)
        `when`(lruCache.remove(key)).thenReturn(smallResource)
        `when`(lruCache.put(key, smallResource)).thenReturn(smallResource)

        val cachedResources = HashMap<String, Resource>()
        cachedResources[key] = smallResource
        `when`(lruCache.snapshot()).thenReturn(cachedResources)
    }

    @Test
    fun init_new_cache() {

        assertEquals(cache.maxSize(), four_mb)
        verify(lruCache).maxSize()
    }

    @Test
    fun add_bigger_resource_than_max_size() {
        cache[key] = bigResource

        verify(lruCache).maxSize()
        verify(lruCache).remove(key)
    }

    @Test
    fun add_resource_success() {
        cache[key] = smallResource

        verify(lruCache).maxSize()
        verify(lruCache).put(key, smallResource)
    }

    @Test
    fun clear_key_doesnt_exist() {
        cache.clearKey(key + 1)

        verify(lruCache).snapshot()
        verifyNoMoreInteractions(lruCache)
    }


    @Test
    fun clear_key_exist() {
        cache.clearKey(key)

        verify(lruCache).snapshot()
        verify(lruCache).remove(key)
    }

}