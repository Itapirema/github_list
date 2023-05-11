package br.com.challenge.github.data.api

import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.data.dto.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UserDTO>?

    @GET("users/{username}")
    suspend fun getUserByName(@Path("username") name: String): UserDTO?

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") name: String): List<RepositoryDTO>?

}