package com.raihan.testportal.ui.thirdpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.raihan.testportal.data.model.User
import com.raihan.testportal.data.repository.UserPagingRepository
import kotlinx.coroutines.flow.Flow

class ThirdPageViewModel(
    userPagingRepository: UserPagingRepository
) : ViewModel() {

    val userPagingFlow: Flow<PagingData<User>> = userPagingRepository.getUsers()
        .cachedIn(viewModelScope)
}

