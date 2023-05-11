package br.com.challenge.github.data.repository

import br.com.challenge.github.data.api.RequestCallBack
import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.data.dto.UserDTO

interface Repository {
    suspend fun getUsers(callback: RequestCallBack<List<UserDTO>>)
    suspend fun getUserByName(userName: String, callback: RequestCallBack<UserDTO>)
    suspend fun getUserRepositories(userName: String, callback: RequestCallBack<List<RepositoryDTO>>)
}