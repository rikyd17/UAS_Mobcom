package com.dicoding.rotie.presentation.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.rotie.R
import com.dicoding.rotie.presentation.ui.common.Converter
import com.dicoding.rotie.presentation.ui.common.UiState
import com.dicoding.rotie.presentation.ui.components.OrderButton
import com.dicoding.rotie.presentation.ui.components.ProductCounter
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat

@Composable
fun DetailScreen(
	breadId: Long,
	viewModel: DetailViewModel = koinViewModel(),
	navigateBack: () -> Unit,
	navigateToCart: () -> Unit,
) {
	val addToCartState by viewModel.addToCartState.collectAsState()
	viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
		when (uiState) {
			is UiState.Loading -> {
				viewModel.getBreadById(breadId)
			}

			is UiState.Success -> {
				val data = uiState.data
				DetailContent(
					image = data.image,
					title = data.name,
					basePrice = data.price,
					description = data.description,
					onBackClick = navigateBack,
					onAddToCart = { count ->
						viewModel.addToCart(data, count)
					},
					addToCartState = addToCartState,
					navigateToCart = navigateToCart
				)
			}

			is UiState.Error -> {}
		}
	}
}

@Composable
fun DetailContent(
	title: String,
	basePrice: Int,
	description: String,
	image: String,
	onBackClick: () -> Unit,
	onAddToCart: (count: Int) -> Unit,
	addToCartState: UiState<Unit>,
	navigateToCart: () -> Unit,
	modifier: Modifier = Modifier,
) {
	var totalPrice by rememberSaveable { mutableIntStateOf(0) }
	var orderCount by rememberSaveable { mutableIntStateOf(0) }

	LaunchedEffect(addToCartState) {
		if (addToCartState is UiState.Success)
			navigateToCart()
	}

	Column(modifier = modifier) {
		Column(
			modifier = Modifier
				.verticalScroll(rememberScrollState())
				.weight(1f)
		) {
			Box {
				AsyncImage(
					model = image,
					contentDescription = null,
					contentScale = ContentScale.Crop,
					modifier = modifier
						.height(400.dp)
						.fillMaxWidth()
						.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
				)
				Icon(
					imageVector = Icons.Default.ArrowBack,
					contentDescription = null,
					modifier = Modifier
						.padding(16.dp)
						.clickable { onBackClick() }
				)
			}
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween,
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			) {
				Text(
					text = title,
					style = MaterialTheme.typography.headlineMedium,
				)
				Text(
					text = stringResource(
						id = R.string.prices,
						Converter.rupiah(basePrice)
					),
					style = MaterialTheme.typography.headlineSmall,
					color = MaterialTheme.colorScheme.secondary,
				)
			}
			Text(
				text = description,
				style = MaterialTheme.typography.bodyMedium,
				textAlign = TextAlign.Justify,
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp)
			)
		}
		Spacer(
			modifier = Modifier
				.fillMaxWidth()
				.height(4.dp)
				.background(Color.LightGray)
		)
		Column(modifier.padding(16.dp)) {
			ProductCounter(
				orderId = 1,
				orderCount = orderCount,
				onProductIncreased = { orderCount++ },
				onProductDecreased = { orderCount-- },
				modifier = Modifier
					.align(Alignment.CenterHorizontally)
					.padding(bottom = 16.dp)
			)
			totalPrice = basePrice * orderCount
			OrderButton(
				text = stringResource(id = R.string.add_to_cart, totalPrice),
				enabled = orderCount > 0,
				onClick = {
					onAddToCart(orderCount)
				}
			)
		}
	}
}