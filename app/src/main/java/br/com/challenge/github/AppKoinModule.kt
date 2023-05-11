package br.com.challenge.github

import br.com.challenge.github.data.repository.*
import br.com.challenge.github.domain.*
import br.com.challenge.github.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object AppKoinModule {
    val module = module {
        viewModelOf(::MainViewModel)
        factoryOf(::UserUseCaseImpl) { bind<UserUseCase>() }
        factoryOf(::RepositoryImpl) { bind<Repository>() }
    }
}