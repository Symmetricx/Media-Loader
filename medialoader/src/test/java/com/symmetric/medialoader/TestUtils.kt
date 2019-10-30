package com.symmetric.medialoader

import android.net.Uri
import com.symmetric.medialoader.network.ResourceRequest
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


fun generateByteArray(size: Int = 1): ByteArray{
    return ByteArray(size)
}


fun generateResourceRequest(): ResourceRequest {
    val uri = mock(Uri::class.java)
    `when`(uri.toString()).thenReturn(TestConstants.example_url)
    return ResourceRequest.Builder(uri).build()
}