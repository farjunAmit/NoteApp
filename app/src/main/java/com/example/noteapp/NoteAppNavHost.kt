package com.example.noteapp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.ui.screens.GateScreen
import com.example.noteapp.ui.screens.LoginScreen
import com.example.noteapp.ui.screens.NoteDetailsScreen
import com.example.noteapp.ui.screens.NotesScreen
import com.example.noteapp.ui.vm.AuthViewModel
import com.example.noteapp.ui.vm.NoteDetailsViewModel
import com.example.noteapp.ui.vm.NoteScreenViewModel

@Composable
fun NoteAppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.GATE) {
        composable(Routes.GATE) {
            val viewModel: AuthViewModel = hiltViewModel()
            GateScreen(
                viewModel = viewModel,
                onUserAlreadyLoggedIn = {
                    navController.navigate(Routes.NOTE) {
                        popUpTo(Routes.GATE) { inclusive = true }
                    }
                },
                onUserNotLoggedIn = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.GATE) { inclusive = true }
                    }
                }
            )
        }


        composable(Routes.NOTE) {
            val noteViewModel: NoteScreenViewModel = hiltViewModel()
            val authViewModel: AuthViewModel = hiltViewModel()
            NotesScreen(
                noteScreenViewModel = noteViewModel,
                onNoteClick = { noteId ->
                    navController.navigate(Routes.noteDetails(noteId))
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.NOTE) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(
                authViewModel = viewModel,
                onLoginSuccess = { navController.navigate(Routes.NOTE) }
            )
        }

        composable(
            Routes.NOTE_DETAILS, arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                }
            )
        )
        { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            val viewModel: NoteDetailsViewModel = hiltViewModel()
            NoteDetailsScreen(
                noteId = noteId,
                noteDetailsViewModel = viewModel,
                onSaveClick = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }

    }

}