package com.dicoding.rotie.presentation.ui.screen.cart

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dicoding.rotie.R
import com.dicoding.rotie.domain.model.BreadOrder
import com.dicoding.rotie.domain.model.Order
import com.dicoding.rotie.presentation.ui.common.Converter
import com.dicoding.rotie.presentation.ui.common.UiState
import com.dicoding.rotie.presentation.ui.components.CartItem
import com.dicoding.rotie.presentation.ui.components.OrderButton
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat

@Composable
fun CartScreen(
	viewModel: CartViewModel = koinViewModel(),
) {
	val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)
	LaunchedEffect(key1 = Unit) {
		viewModel.getAddedProductOrders()
	}
	CartContent(
		state = (uiState as? UiState.Success)?.data ?: CartState(
			totalPrice = 0
		),
		onProductCountChanged = { orderId, count ->
			viewModel.updateProductOrder(orderId, count)
		}
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
	state: CartState,
	onProductCountChanged: (id: Long, count: Int) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(modifier = modifier.fillMaxSize()) {
		TopAppBar(
			title = {
				Text(
					text = stringResource(id = R.string.menu_cart),
					style = MaterialTheme.typography.headlineLarge,
				)
			},
			colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surface),
		)
		OrderButton(
			text = stringResource(
				id = R.string.total_order, Converter.rupiah(state.totalPrice)
			), enabled = state.order.isNotEmpty(), onClick = {}, modifier = Modifier.padding(16.dp)
		)

		LazyColumn(
			contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			items(state.order, key = { item -> item.id }) { item ->
				CartItem(
					orderId = item.id,
					image = item.bread.image,
					title = item.bread.name,
					totalPrice = item.bread.price * item.count,
					count = item.count,
					onProductCountChanged = onProductCountChanged,
				)
				Divider()
			}
		}
	}
}