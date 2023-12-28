package com.dicoding.rotie.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.rotie.R
import com.dicoding.rotie.presentation.ui.common.Converter
import java.text.NumberFormat

@Composable
fun CartItem(
	orderId: Long,
	image: String,
	title: String,
	totalPrice: Int,
	count: Int,
	onProductCountChanged: (id: Long, count: Int) -> Unit,
	modifier: Modifier = Modifier
) {
	Row(modifier = modifier.fillMaxWidth()) {
		AsyncImage(
			model = image,
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.size(90.dp)
				.clip(ShapeDefaults.Small)
		)
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(8.dp)
				.weight(1.0f)
		) {
			Text(
				text = title,
				maxLines = 3,
				overflow = TextOverflow.Ellipsis,
				style = MaterialTheme.typography.labelMedium
			)
			Text(
				text = Converter.rupiah(totalPrice),
				color = MaterialTheme.colorScheme.secondary,
				style = MaterialTheme.typography.labelSmall
			)
		}
		ProductCounter(
			orderId = orderId,
			orderCount = count,
			onProductIncreased = { onProductCountChanged(orderId, count + 1) },
			onProductDecreased = { onProductCountChanged(orderId, count - 1) },
			modifier = Modifier.padding(8.dp)
		)
	}
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
	CartItem(
		orderId = 0,
		image = "",
		title = "Croissant",
		totalPrice = 12000,
		count = 0,
		onProductCountChanged = { _, _ -> },
	)
}