package com.dicoding.rotie.presentation.ui.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.rotie.R
import com.dicoding.rotie.domain.model.Bread
import com.dicoding.rotie.presentation.ui.common.Converter
import com.dicoding.rotie.presentation.ui.common.UiState
import com.dicoding.rotie.presentation.ui.theme.RotieTheme
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HomeScreen(
	modifier: Modifier = Modifier,
	viewModel: HomeViewModel = koinViewModel(),
	navigateToDetail: (Long) -> Unit,
) {
	viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
		when (uiState) {
			is UiState.Loading -> {
				viewModel.getAllBreads()
			}

			is UiState.Success -> {
				Log.d("HomeScreen", uiState.data.toString())
				HomeContent(
					breads = uiState.data,
					modifier = modifier.fillMaxSize(),
					navigateToDetail = navigateToDetail,
					onSearchQueryChange = viewModel::searchBread,
				)
			}

			is UiState.Error -> {
				Log.e("HomeScreen", uiState.errorMessage)
			}
		}
	}
}

@Composable
fun HomeContent(
	breads: List<Bread>,
	modifier: Modifier = Modifier,
	navigateToDetail: (Long) -> Unit,
	onSearchQueryChange: (String) -> Unit,
) {
	Column(
		modifier = modifier,
	) {
		SearchBar(
			onQueryChange = onSearchQueryChange,
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		)
		LazyVerticalGrid(
			columns = GridCells.Adaptive(160.dp),
			contentPadding = PaddingValues(16.dp),
			horizontalArrangement = Arrangement.spacedBy(16.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
		) {
			items(breads) { data ->
				ProductItem(
					image = data.image,
					title = data.name,
					price = data.price,
					modifier = Modifier
						.clickable { navigateToDetail(data.id) }
				)
			}
		}
	}
}

@Composable
fun ProductItem(
	title: String,
	price: Int,
	image: String,
	modifier: Modifier = Modifier,
) {
	Column(
		modifier = modifier,
	) {
		AsyncImage(
			model = image,
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(170.dp)
				.clip(Shapes().medium)
		)
		Text(
			text = title,
			maxLines = 2,
			overflow = TextOverflow.Ellipsis,
			style = MaterialTheme.typography.labelMedium
		)
		Text(
			text = stringResource(
				id = R.string.prices,
				Converter.rupiah(price)
			),
			style = MaterialTheme.typography.labelSmall,
		)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
	modifier: Modifier = Modifier,
	onQueryChange: (String) -> Unit,
) {
	var query by remember { mutableStateOf("") }
	OutlinedTextField(
		value = query,
		onValueChange = {
			query = it
			onQueryChange(query)
		},
		leadingIcon = {
			Icon(
				imageVector = Icons.Filled.Search,
				contentDescription = null,
			)
		},
		singleLine = true,
		shape = RoundedCornerShape(28.dp),
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
	RotieTheme {
		ProductItem(
			image = "https://leiowbrqulmqfzmchfwq.supabase.co/storage/v1/object/public/bread_image/melon_pan.jpg",
			title = "Melon Pan",
			price = 7000
		)
	}
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
	RotieTheme {
		SearchBar(onQueryChange = { }, modifier = Modifier.fillMaxWidth())
	}

}