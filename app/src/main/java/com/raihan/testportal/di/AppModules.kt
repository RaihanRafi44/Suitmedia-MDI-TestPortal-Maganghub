package com.raihan.testportal.di

import com.raihan.testportal.data.datasource.UserDataSource
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val networkModule =
        module {
            //single<TestPortalApiService> { TestPortalApiService.invoke() }
        }

    private val datasource =
        module {
            //single<UserDataSource> { UserApiDataSource(get()) }
        }

    private val paging =
        module {
            //single<UserPagingSource> { UserPagingSource(get()) }
        }

    private val repository =
        module {
            //single<UserRepository> { UserRepositoryImpl(get()) }
            //single<UserPagingRepository> { UserPagingRepositoryImpl(get()) }
        }

    private val viewModel =
        module {
            //viewModelOf(::ThirdScreenViewModel)
        }

    val modules =
        listOf(
            networkModule,
            datasource,
            paging,
            repository,
            viewModel,
        )
}