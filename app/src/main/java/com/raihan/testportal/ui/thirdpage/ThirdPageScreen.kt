package com.raihan.testportal.ui.thirdpage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.raihan.testportal.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdPageScreen(
    onBackClick: () -> Unit,
    onUserClick: (String) -> Unit,
    viewModel: ThirdPageViewModel = koinViewModel()
) {

    val userPagingItems = viewModel.userPagingFlow.collectAsLazyPagingItems()
    val refreshState = userPagingItems.loadState.refresh
    val isRefreshError = refreshState is LoadState.Error
    val hasData = userPagingItems.itemCount > 0

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Third Screen",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black)
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Back",
                            modifier = Modifier.size(18.dp),
                            tint = Color.Blue
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        SwipeRefresh(
            state = rememberSwipeRefreshState(
                isRefreshing = refreshState is LoadState.Loading
            ),
            onRefresh = { userPagingItems.refresh() },
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                if (hasData && !isRefreshError) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 20.dp)
                    ) {
                        items(userPagingItems.itemCount) { index ->
                            val user = userPagingItems[index]
                            if (user != null) {
                                UserItem(
                                    user = user,
                                    onItemClick = { onUserClick(it) }
                                )

                                if (index < userPagingItems.itemCount - 1) {
                                    HorizontalDivider(
                                        color = Color.LightGray,
                                        thickness = 0.5.dp,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                            }
                        }

                        if (userPagingItems.loadState.append is LoadState.Loading) {
                            item { PaginationLoadingFooter(isLoading = true, onRetry = {}) }
                        }

                        if (userPagingItems.loadState.append is LoadState.Error) {
                            item { PaginationLoadingFooter(isLoading = false, onRetry = { userPagingItems.retry() }) }
                        }
                    }
                }

                if (isRefreshError) {
                    val errorState = refreshState as LoadState.Error
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Error",
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp).padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Failed load data.\n${errorState.error.localizedMessage}",
                            textAlign = TextAlign.Center,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(onClick = { userPagingItems.retry() }) {
                            Text("Try Again")
                        }
                    }
                }

                if (refreshState is LoadState.NotLoading && !hasData) {
                    Text(
                        text = "No Data Available",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

