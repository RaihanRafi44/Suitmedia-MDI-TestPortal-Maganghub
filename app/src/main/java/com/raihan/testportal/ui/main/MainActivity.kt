package com.raihan.testportal.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raihan.testportal.ui.firstpage.FirstPageScreen
import com.raihan.testportal.ui.secondpage.SecondPageScreen
import com.raihan.testportal.ui.theme.TestPortalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT
            )
        )
        setContent {
            TestPortalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "first_screen"
                    ) {
                        composable("first_screen") {
                            FirstPageScreen(
                                onNextClick = { name ->
                                    navController.navigate("second_screen/$name")
                                }
                            )
                        }

                        composable(
                            route = "second_screen/{name}",
                            arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("name") ?: ""

                            SecondPageScreen(
                                userName = name,
                                onBackClick = {
                                    navController.popBackStack()
                                },
                                onChooseUserClick = {}
                            )

                        }
                    }
                    //FirstPageScreen()
                }
            }
        }
    }
}
