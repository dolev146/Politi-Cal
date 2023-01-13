package com.example.politi_cal.data.dto

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface PostService {

    companion object {
        fun create(): PostService {
            return PostsServiceImpl(
                client = HttpClient(Android){
                    install(Logging){
                        level = LogLevel.ALL
                    }
                    install(JsonFeature){
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }


    suspend fun getUsers(): List<PostResponse>

    suspend fun createUser(postRequest: PostRequest): PostResponse?

    suspend fun getVotes(): List<PostResponse>

    suspend fun createVote(postRequest: PostRequest): PostResponse?

    suspend fun getCelebs(): List<PostResponse>

    suspend fun createCeleb(postRequest: PostRequest): PostResponse?




}