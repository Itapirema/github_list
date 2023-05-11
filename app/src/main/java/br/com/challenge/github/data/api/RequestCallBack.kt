package br.com.challenge.github.data.api

interface RequestCallBack <T> {
    fun onSuccess(value: T)
    fun onFailure(message: String)
    fun onProgress(shouldShow: Boolean)
}