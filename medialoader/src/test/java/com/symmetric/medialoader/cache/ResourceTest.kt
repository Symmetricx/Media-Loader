package com.symmetric.medialoader.cache

import android.graphics.Bitmap
import com.symmetric.medialoader.cache.ResourceType.BITMAP
import com.symmetric.medialoader.cache.ResourceType.BYTE_ARRAY
import com.symmetric.medialoader.cache.ResourceType.EMPTY
import com.symmetric.medialoader.generateByteArray
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResourceTest {

    @Mock
    private lateinit var bitmap: Bitmap


    @Test
    fun empty_builder() {
        val resource = Resource.Builder().build()

        assertNull(resource.bitmap)
        assertNull( resource.byteArray)
        assertEquals(EMPTY, resource.type)
    }

    @Test
    fun builder_with_bitmap() {
        val resource = Resource.Builder(bitmap).build()

        assertNotNull(resource.bitmap)
        assertEquals(BITMAP, resource.type)
    }

    @Test
    fun builder_with_byte_array() {
        val arraySize = 100
        val resource = Resource.Builder(generateByteArray(arraySize)).build()

        assertNotNull(resource.byteArray)
        assertEquals(arraySize ,resource.byteCount)
        assertEquals(BYTE_ARRAY, resource.type)
    }

    @Test
    fun builder_with_resource() {
        val arraySize = 100

        val resource = Resource.Builder(generateByteArray(arraySize)).build()
        val secondResource = Resource.Builder(resource).build()

        assertNotNull(secondResource.byteArray)
        assertEquals(arraySize, secondResource.byteCount)
        assertEquals(BYTE_ARRAY, secondResource.type)
    }
}