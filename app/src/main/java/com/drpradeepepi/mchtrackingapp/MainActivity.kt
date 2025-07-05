// File: app/src/main/java/com/drpradeepepi/mchtrackingapp/MainActivity.kt
package com.drpradeepepi.mchtrackingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drpradeepepi.mchtrackingapp.ui.theme.MCHTrackingAppTheme
import com.drpradeepepi.mchtrackingapp.viewmodel.*
import com.drpradeepepi.mchtrackingapp.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MCHTrackingAppTheme {
                MCHTrackingApp()
            }
        }
    }
}

@Composable
fun MCHTrackingApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as? MCHApplication
        ?: throw IllegalStateException(
            "Application is not MCHApplication. Check AndroidManifest.xml for android:name declaration."
        )

    // Create ViewModels with factories
    val phcViewModel: PHCViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return PHCViewModel(application.phcRepository) as T
            }
        }
    )

    val hscViewModel: HSCViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return HSCViewModel(application.hscRepository) as T
            }
        }
    )

    val motherViewModel: MotherViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return MotherViewModel(application.motherRepository) as T
            }
        }
    )

    val followUpViewModel: FollowUpViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return FollowUpViewModel(application.followUpRepository) as T
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = "phc_list"
    ) {
        composable("phc_list") {
            PHCListScreen(
                navController = navController,
                viewModel = phcViewModel
            )
        }
        composable("hsc_list/{phcName}") { backStackEntry ->
            val phcName = backStackEntry.arguments?.getString("phcName") ?: ""
            HSCListScreen(
                navController = navController,
                phcName = phcName,
                viewModel = hscViewModel
            )
        }
        composable("mothers_list/{hscName}") { backStackEntry ->
            val hscName = backStackEntry.arguments?.getString("hscName") ?: ""
            MothersListScreen(
                navController = navController,
                hscName = hscName,
                viewModel = motherViewModel
            )
        }
        composable("mother_detail/{motherName}") { backStackEntry ->
            val motherName = backStackEntry.arguments?.getString("motherName") ?: ""
            MotherDetailScreen(
                navController = navController,
                motherName = motherName,
                motherViewModel = motherViewModel,
                followUpViewModel = followUpViewModel
            )
        }
        composable("add_follow_up/{motherName}") { backStackEntry ->
            val motherName = backStackEntry.arguments?.getString("motherName") ?: ""
            AddFollowUpScreen(
                navController = navController,
                motherName = motherName,
                viewModel = followUpViewModel
            )
        }
        composable("edit_follow_up/{motherName}/{followUpId}") { backStackEntry ->
            val motherName = backStackEntry.arguments?.getString("motherName") ?: ""
            val followUpId = backStackEntry.arguments?.getString("followUpId")?.toIntOrNull() ?: 0
            EditFollowUpScreen(
                navController = navController,
                motherName = motherName,
                followUpId = followUpId,
                viewModel = followUpViewModel
            )
        }
        composable("add_phc") {
            AddPHCScreen(
                navController = navController,
                viewModel = phcViewModel
            )
        }
        composable("edit_phc/{phcName}") { backStackEntry ->
            val phcName = backStackEntry.arguments?.getString("phcName") ?: ""
            EditPHCScreen(
                navController = navController,
                phcName = phcName,
                viewModel = phcViewModel
            )
        }
        composable("add_hsc/{phcName}") { backStackEntry ->
            val phcName = backStackEntry.arguments?.getString("phcName") ?: ""
            AddHSCScreen(
                navController = navController,
                phcName = phcName,
                viewModel = hscViewModel
            )
        }
        composable("edit_hsc/{phcName}/{hscName}") { backStackEntry ->
            val phcName = backStackEntry.arguments?.getString("phcName") ?: ""
            val hscName = backStackEntry.arguments?.getString("hscName") ?: ""
            EditHSCScreen(
                navController = navController,
                phcName = phcName,
                hscName = hscName,
                viewModel = hscViewModel
            )
        }
        composable("add_mother/{hscName}") { backStackEntry ->
            val hscName = backStackEntry.arguments?.getString("hscName") ?: ""
            AddMotherScreen(
                navController = navController,
                hscName = hscName,
                viewModel = motherViewModel
            )
        }
        composable("edit_mother/{hscName}/{motherName}") { backStackEntry ->
            val hscName = backStackEntry.arguments?.getString("hscName") ?: ""
            val motherName = backStackEntry.arguments?.getString("motherName") ?: ""
            EditMotherScreen(
                navController = navController,
                hscName = hscName,
                motherName = motherName,
                viewModel = motherViewModel
            )
        }
    }
}