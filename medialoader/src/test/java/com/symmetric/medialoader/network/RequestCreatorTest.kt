package com.symmetric.medialoader.network

import android.net.Uri
import com.symmetric.medialoader.MediaLoader
import com.symmetric.medialoader.cache.LruCache
import com.symmetric.medialoader.cache.Resource
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RequestCreatorTest {


    @Mock
    internal lateinit var lruCache: LruCache<String, Resource>

    @Mock
    internal lateinit var requestHandler: RequestHandler<String, Resource>

    @Mock
    internal lateinit var uri: Uri

    lateinit var requestCreator: RequestCreator

    @Before
    fun setup() {
        requestCreator = RequestCreator(lruCache, uri, requestHandler)
    }

    @Test
    fun listen_to_byte_array_add_success() {
        assertEquals(
            "uri\nfalse" to 0,
            requestCreator.listen(mock(RequestHandler.Callback::class.java))
        )
    }

    @Test
    fun listen_to_byte_array_get_cached() {
        assertEquals(
            "uri\nfalse" to 0,
            requestCreator.listen(mock(RequestHandler.Callback::class.java))
        )

        assertEquals(
            "uri\nfalse" to 1,
            requestCreator.listen(mock(RequestHandler.Callback::class.java))
        )
    }


}