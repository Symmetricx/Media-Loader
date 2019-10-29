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

        assertEquals(resource.bitmap, null)
        assertEquals(resource.byteArray, null)
        assertEquals(resource.type, EMPTY)
    }

    @Test
    fun builder_with_bitmap() {
        val resource = Resource.Builder(bitmap).build()

        assertNotNull(resource.bitmap)
        assertEquals(resource.type, BITMAP)
    }

    @Test
    fun builder_with_byte_array() {
        val arraySize = 100
        val resource = Resource.Builder(generateByteArray(arraySize)).build()

        assertNotNull(resource.byteArray)
        assertEquals(resource.byteCount, arraySize)
        assertEquals(resource.type, BYTE_ARRAY)
    }

    @Test
    fun builder_with_resource() {
        val arraySize = 100

        val resource = Resource.Builder(generateByteArray(arraySize)).build()
        val secondResource = Resource.Builder(resource).build()

        assertNotNull(secondResource.byteArray)
        assertEquals(secondResource.byteCount, arraySize)
        assertEquals(secondResource.type, BYTE_ARRAY)
    }
}