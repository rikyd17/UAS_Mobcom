package com.dicoding.rotie.presentation.ui.screen.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.rotie.domain.model.UserProfile
import com.dicoding.rotie.presentation.ui.common.UiState
import io.github.jan.supabase.compose.auth.composable.rememberSignOutWithGoogle
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
	navigateToLogin: () -> Unit,
	viewModel: ProfileViewModel = koinViewModel()
) {
	LaunchedEffect(key1 = Unit) {
		viewModel.getProfile()
	}
	val authState = viewModel.composeAuth.rememberSignOutWithGoogle()
	val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)
	Box(
		contentAlignment = Alignment.Center,
		modifier = Modifier.fillMaxSize()
	) {
		Column (
			horizontalAlignment = Alignment.CenterHorizontally
		){
			if (uiState is UiState.Success) {
				AsyncImage(
					model = (uiState as UiState.Success<UserProfile>).data.avatarUrl,
					contentDescription = null,
					contentScale = ContentScale.Fit,
					modifier = Modifier
						.size(120.dp)
						.clip(CircleShape)
				)
				Text(
					text = (uiState as UiState.Success<UserProfile>).data.name,
					style = MaterialTheme.typography.headlineMedium,
					textAlign = TextAlign.Center,
					modifier = Modifier.fillMaxWidth()
				)
				Text(
					text = (uiState as UiState.Success<UserProfile>).data.email,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.secondary,
					textAlign = TextAlign.Center,
					modifier = Modifier.fillMaxWidth()
				)
			}
			Button(onClick = {
				authState.startFlow()
				navigateToLogin()
			}) {
				Text(text = "Sign Out")
			}
		}
	}
}