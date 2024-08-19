package com.example.weather.presentation

import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.R
import com.example.weather.models.WeatherResponse


@Composable
fun WeatherScreen(weatherResponse: WeatherResponse?) {

    val preferences: SharedPreferences

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.main_screen_content_background_color))
            .padding(dimensionResource(id = R.dimen.main_screen_content_padding))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            WeatherCardsFirstRow(weatherResponse)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.card_view_marginTop)))
        Row {
            WeatherCardsSecondRow()
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.card_view_marginTop)))
        LocationCard()
    }
    }


@Composable
fun WeatherCardsFirstRow(weatherResponse: WeatherResponse?) {

    Row {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.card_view_marginStartEnd)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_view_corner_radius)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_view_elevation))
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(size = 40.dp),
                    contentDescription = "",
                    painter = painterResource(id = R.drawable.snowflakebk)
                )
                Column {
                    Text(
                        text = weatherResponse?.main?.tempMax.toString(),
                        color = colorResource(id = R.color.primary_text_color),
                        fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "condition",
                        color = colorResource(id = R.color.secondary_text_color),
                        fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.card_view_marginStartEnd)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_view_corner_radius)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_view_elevation))
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(size = 40.dp),
                    contentDescription = "",
                    painter = painterResource(id = R.drawable.rain)
                )
                Column {
                    Text(
                        text = "Degree",
                        color = colorResource(id = R.color.primary_text_color),
                        fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "percent",
                        color = colorResource(id = R.color.secondary_text_color),
                        fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCardsSecondRow() {

    Row {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.card_view_marginStartEnd)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_view_corner_radius)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_view_elevation))
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(size = 40.dp),
                    contentDescription = "",
                    painter = painterResource(id = R.drawable.wind)
                )
                Column {
                    Text(
                        text = "Maximum",
                        color = colorResource(id = R.color.primary_text_color),
                        fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "minimum",
                        color = colorResource(id = R.color.secondary_text_color),
                        fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(id = R.dimen.card_view_marginStartEnd)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_view_corner_radius)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_view_elevation))
        ) {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(size = 40.dp),
                    contentDescription = "",
                    painter = painterResource(id = R.drawable.sunny)
                )
                Column {
                    Text(
                        text = "Wind",
                        color = colorResource(id = R.color.primary_text_color),
                        fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "miles/hour",
                        color = colorResource(id = R.color.secondary_text_color),
                        fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}


@Composable
fun LocationCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.card_view_marginStartEnd)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_view_corner_radius)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.card_view_elevation))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.card_view_content_padding))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = stringResource(id = R.string.image_contentDescription),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.identification_image_size))
                        .padding(end = dimensionResource(id = R.dimen.identification_image_marginEnd)),
                    contentScale = ContentScale.Fit
                )
                Column {
                    Text(
                        text = "Name",
                        color = colorResource(id = R.color.primary_text_color),
                        fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Text(
                        text = "Country",
                        color = colorResource(id = R.color.secondary_text_color),
                        fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.divider_margin)),
                thickness = dimensionResource(id = R.dimen.divider_height),
                color = colorResource(id = R.color.divider_background)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.sunrise_marginTop))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sunrise),
                        contentDescription = stringResource(id = R.string.image_contentDescription),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.identification_image_size)),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Sunrise",
                        color = colorResource(id = R.color.secondary_text_color),
                        fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.sunset_marginStart))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.sunset),
                        contentDescription = stringResource(id = R.string.image_contentDescription),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.identification_image_size)),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Sunset",
                        color = colorResource(id = R.color.secondary_text_color),
                        fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherTheme() {

}
