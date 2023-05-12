package br.com.challenge.github.data.repository

import br.com.challenge.github.data.api.ApiClient
import br.com.challenge.github.data.api.ApiService
import br.com.challenge.github.data.api.RequestCallBack
import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.data.dto.UserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RepositoryImpl : Repository {

    companion object {
        private const val CODE_404 = 404
        private const val MSG_NOT_FOUND = "Usuário não encontrado."
        private const val MSG_ERROR_DEFAULT = "Falha ao carregar os dados."
    }

    override suspend fun getUsers(callback: RequestCallBack<List<UserDTO>>) =
        withContext(Dispatchers.Main) {
            try {
                callback.onProgress(true)
                val users = ApiClient.create(ApiService::class.java).getUsers()
                callback.onProgress(false)
                if (users == null) {
                    callback.onFailure(MSG_ERROR_DEFAULT)
                } else callback.onSuccess(users)
            } catch (e: HttpException) {
                callback.onProgress(false)
                callback.onFailure(MSG_ERROR_DEFAULT)
            } catch (e: Throwable) {
                callback.onProgress(false)
                callback.onFailure(MSG_ERROR_DEFAULT)
            }
        }

    override suspend fun getUserByName(userName: String, callback: RequestCallBack<UserDTO>) =
        withContext(Dispatchers.Main) {
            try {
                callback.onProgress(true)
                val user = ApiClient.create(ApiService::class.java).getUserByName(userName)
                callback.onProgress(false)
                if (user == null) {
                    callback.onFailure(MSG_ERROR_DEFAULT)
                } else callback.onSuccess(user)
            } catch (e: HttpException) {
                callback.onProgress(false)
                val message = if (e.code() == CODE_404) MSG_NOT_FOUND else MSG_ERROR_DEFAULT
                callback.onFailure(message)
            } catch (e: Throwable) {
                callback.onProgress(false)
                callback.onFailure(MSG_ERROR_DEFAULT)
            }
        }

    override suspend fun getUserRepositories(
        userName: String,
        callback: RequestCallBack<List<RepositoryDTO>>
    ) = withContext(Dispatchers.Main) {
        try {
            callback.onProgress(true)
            val repos = ApiClient.create(ApiService::class.java).getUserRepositories(userName)
            callback.onProgress(false)
            if (repos == null) {
                callback.onFailure(MSG_ERROR_DEFAULT)
            } else callback.onSuccess(repos)
        } catch (e: HttpException) {
            callback.onProgress(false)
            callback.onFailure(MSG_ERROR_DEFAULT)
        } catch (e: Throwable) {
            callback.onProgress(false)
            callback.onFailure(MSG_ERROR_DEFAULT)
        }
    }
}