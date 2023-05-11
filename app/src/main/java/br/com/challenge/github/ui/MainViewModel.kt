package br.com.challenge.github.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.challenge.github.data.dto.RepositoryDTO
import br.com.challenge.github.data.api.RequestCallBack
import br.com.challenge.github.data.dto.UserDTO
import br.com.challenge.github.domain.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    private val _userList = MutableLiveData<List<UserDTO>>()
    val userList: LiveData<List<UserDTO>> = _userList

    private val _repositoryList = MutableLiveData<List<RepositoryDTO>>()
    val repositoryList: LiveData<List<RepositoryDTO>> = _repositoryList

    private val _user = MutableLiveData<UserDTO>()
    val user: LiveData<UserDTO> = _user

    private val _selectedUser = MutableLiveData<UserDTO>()
    val selectedUser: LiveData<UserDTO> = _selectedUser

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String> = _showError

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean> = _showProgress

    fun loadUsers() {
        viewModelScope.launch {
            userUseCase.loadUsers(object : RequestCallBack<List<UserDTO>> {
                override fun onSuccess(value: List<UserDTO>) {
                    _userList.value = value
                }

                override fun onFailure(message: String) {
                    _showError.value = message
                }

                override fun onProgress(shouldShow: Boolean) {
                    _showProgress.value = shouldShow
                }
            })

        }
    }

    fun loadUserByName(userName: String) {
        viewModelScope.launch {
            userUseCase.loadUserByName(userName, object : RequestCallBack<UserDTO> {
                override fun onSuccess(value: UserDTO) {
                    _user.value = value
                }

                override fun onFailure(message: String) {
                    _showError.value = message
                }

                override fun onProgress(shouldShow: Boolean) {
                    _showProgress.value = shouldShow
                }
            })
        }
    }

    fun loadUserRepositories(userName: String) {
        viewModelScope.launch {
            userUseCase.loadUserRepositories(
                userName,
                object : RequestCallBack<List<RepositoryDTO>> {
                    override fun onSuccess(value: List<RepositoryDTO>) {
                        _repositoryList.value = value
                    }

                    override fun onFailure(message: String) {
                        _showError.value = message
                    }

                    override fun onProgress(shouldShow: Boolean) {
                        _showProgress.value = shouldShow
                    }
                })
        }
    }

    fun setSelectedUser(user: UserDTO) {
        _selectedUser.value = user
    }
}