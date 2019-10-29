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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResourceLruCacheTest {

    @Mock
    lateinit var lruCache: LruCache<String, Resource>

    lateinit var resource: Resource

    //  4 MB
    private val four_mb = 4 * 1024 * 1024

    private val key = "KEY"

    @Before
    fun setUp() {
        resource = Resource
            .Builder(generateByteArray(four_mb + 1))
            .build()

        `when`(lruCache.maxSize()).thenReturn(four_mb)
        `when`(lruCache.remove(key)).thenReturn(resource)
    }

    @Test
    fun init_new_cache() {
        val cache = ResourceLruCache(lruCache)

        assertEquals(cache.maxSize(), four_mb)
        verify(lruCache).maxSize()
    }

    @Test
    fun add_bigger_resource_than_max_size() {
        val cache = ResourceLruCache(lruCache)

        cache[key] = resource
        verify(lruCache).maxSize()
        verify(lruCache).remove(key)
    }
}