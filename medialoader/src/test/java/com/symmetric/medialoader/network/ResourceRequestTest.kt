package com.symmetric.medialoader.network

import android.net.Uri
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ResourceRequestTest {

    val url = "www.example.com"

    @Mock
    lateinit var uri: Uri

    @Before
    fun setup(){

    }

    @Test
    fun builder_uri(){
        val resourceRequestBuilder = ResourceRequest.Builder(uri)
        val resourceRequest = resourceRequestBuilder.build()

        assertEquals(0, resourceRequest.id)
        assertEquals(uri, resourceRequest.uri)
        assertEquals(false, resourceRequest.isImage)
        assertEquals(0, resourceRequest.targetHeight)
        assertEquals(0, resourceRequest.targetWidth)
        assertEquals(generateKey(resourceRequestBuilder), resourceRequest.key)
    }

    private fun generateKey(builder: ResourceRequest.Builder): String {
        return "${builder.uri}\n${builder.isImage}"
    }
}