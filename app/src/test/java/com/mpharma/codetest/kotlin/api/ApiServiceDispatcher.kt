package com.mpharma.codetest.kotlin.api

import com.mpharma.codetest.kotlin.getJson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

class ApiServiceDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/5c3e15e63500006e003e9795" -> {
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("response.json"))
            }
            else -> {throw  IllegalArgumentException("Unknown request path: ${request.path}")}
        }
    }
}