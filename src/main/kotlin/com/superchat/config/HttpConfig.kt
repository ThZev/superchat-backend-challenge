package com.superchat.config

import org.eclipse.microprofile.config.inject.ConfigProperty
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpConfig {

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    @Inject
    @ConfigProperty(name = "bitcoin.url")
    lateinit var bitcoinUrl: String

    fun sendGetRequest(uri: String): String {
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(uri))
            .build()
        val httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        return httpResponse.body()
    }
}