package com.example.politi_cal.data.dto

import com.google.firebase.auth.FirebaseAuth
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*


class PostsServiceImpl(
    private val client: HttpClient
) : PostService {
    override suspend fun getUsers(): List<PostResponse> {
        return try {
            client.get {
                url(HttpRoutes.USERS)
            }
        }
        catch (e: Exception) {
            print("Error : ${e}")
            emptyList()
        }

    }

    override suspend fun createUser(postRequest: PostRequest): PostResponse? {
        return try {
            client.post<PostResponse> {
                url(HttpRoutes.USERS)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
        }
        catch (e: Exception) {
            print("Error : ${e}")
            null
        }
    }

    override suspend fun getVotes(): List<PostResponse> {
        return try {
            client.get {
                url(HttpRoutes.VOTES)
            }
        }
        catch (e: Exception) {
            print("Error : ${e}")
            emptyList()
        }
    }

    override suspend fun createVote(postRequest: PostRequest): PostResponse? {
        return try {
            client.post<PostResponse> {
                url(HttpRoutes.VOTES)
                contentType(ContentType.Application.Json)
                body = postRequest
            }
        }
        catch (e: Exception) {
            print("Error : ${e}")
            null
        }
    }

    override suspend fun getCelebs(email : String): List<PostResponse> {
        return try {
            client.get {
                url(HttpRoutes.CELEBS)
                contentType(ContentType.Application.Json)

            }
        }
        catch (e: Exception) {
            print("Error : ${e}")
            emptyList()
        }
    }

    override suspend fun createCeleb(postRequest: PostRequest): PostResponse? {
        return try {
            client.post<PostResponse> {
                url(HttpRoutes.CELEBS)
                contentType(ContentType.Application.Json)
                postRequest.email = FirebaseAuth.getInstance().currentUser?.email.toString()
                body = postRequest
            }
        }
        catch (e: Exception) {
            print("Error : ${e}")
            null
        }
    }

    override suspend fun sendEmail(postRequest: PostRequest): PostResponse? {
        return try {
            client.post<PostResponse> {
                url(HttpRoutes.CELEBS)
                contentType(ContentType.Application.Json)
                println(postRequest.email)
                body = "{'email' : ${postRequest.email}}"
            }
        }
        catch (e: Exception) {
            print("Error : ${e}")
            null
        }
    }
}