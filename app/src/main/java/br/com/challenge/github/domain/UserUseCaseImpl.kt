package br.com.challenge.github.domain

import br.com.challenge.github.data.api.RequestCallBack
import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.data.repository.Repository

class UserUseCaseImpl(private val repository: Repository): UserUseCase {

    override suspend fun loadUsers(callback: RequestCallBack<List<UserDTO>>) {
        repository.getUsers(callback)
    }

    override suspend fun loadUserByName(userName: String, callback: RequestCallBack<UserDTO>) {
        repository.getUserByName(userName, callback)
    }

    override suspend fun loadUserRepositories(
        userName: String,
        callback: RequestCallBack<List<RepositoryDTO>>
    ) {
        repository.getUserRepositories(userName, callback)
    }
}