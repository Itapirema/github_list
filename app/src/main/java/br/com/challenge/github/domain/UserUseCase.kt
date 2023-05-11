package br.com.challenge.github.domain

import br.com.challenge.github.data.api.RequestCallBack
import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.data.dto.UserDTO

interface UserUseCase {
    suspend fun loadUsers(callback: RequestCallBack<List<UserDTO>>)
    suspend fun loadUserByName(userName: String, callback: RequestCallBack<UserDTO>)
    suspend fun loadUserRepositories(userName: String, callback: RequestCallBack<List<RepositoryDTO>>)
}