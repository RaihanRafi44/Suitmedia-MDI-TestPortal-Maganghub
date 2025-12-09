package com.raihan.testportal.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.raihan.testportal.data.mapper.toUserList
import com.raihan.testportal.data.model.User
import com.raihan.testportal.data.source.network.service.TestPortalApiService

class UserPagingSource(
    private val service: TestPortalApiService
) : PagingSource<Int, User>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 6
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        Log.d("UserPagingSource", "Start Load Page: $page")

        return try {
            val response = service.getUsers(page, NETWORK_PAGE_SIZE)

            Log.d("UserPagingSource", "Response success. Data size: ${response.data.size}")

            val users = response.data.toUserList()

            LoadResult.Page(
                data = users,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (users.isEmpty() || users.size < NETWORK_PAGE_SIZE) null else page + 1
            )
        } catch (e: Exception) {

            Log.e("UserPagingSource", "Error Load: ${e.localizedMessage}")
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}


