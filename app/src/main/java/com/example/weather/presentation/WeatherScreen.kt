package com.example.weather.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.domain.util.getUnit
import com.example.weather.domain.util.getUnixTime
import com.example.weather.domain.util.getWeatherIcon


@Composable
fun WeatherScreen(state: WeatherState) {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.main_screen_content_background_color))
            .padding(dimensionResource(id = R.dimen.main_screen_content_padding))
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            WeatherCardsFirstRow(state)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.card_view_marginTop)))
        Row {
            WeatherCardsSecondRow(state)
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.card_view_marginTop)))
        LocationCard(state)
    }
}


@Composable
fun WeatherCardsFirstRow(state: WeatherState) {

    state.weatherData?.let { data ->
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
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(size = 40.dp),
                        contentDescription = "",
                        painter = painterResource(
                            id = getWeatherIcon(
                                data.icon.toString()
                            )
                        )
                    )
                    Column {
                        Text(
                            text = data.main.toString(),
                            color = colorResource(id = R.color.primary_text_color),
                            fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Text(
                            text = data.description.toString(),
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
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(size = 40.dp),
                        contentDescription = "",
                        painter = painterResource(id = R.drawable.humidity)
                    )
                    Column {
                        Text(
                            text = data.temperature.toString() + getUnit(),
                            color = colorResource(id = R.color.primary_text_color),
                            fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Text(
                            text = data.humidity.toString() + "%",
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
}

@Composable
fun WeatherCardsSecondRow(state: WeatherState) {

    state.weatherData?.let { data ->

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
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(size = 40.dp),
                        contentDescription = "",
                        painter = painterResource(id = R.drawable.temperature)
                    )
                    Column {
                        Text(
                            text = data.tempMin.toString() + " min",
                            color = colorResource(id = R.color.primary_text_color),
                            fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Text(
                            text = data.tempMax.toString() + " max",
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
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.card_view_content_padding)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(size = 40.dp),
                        contentDescription = "",
                        painter = painterResource(id = R.drawable.wind)
                    )
                    Column {
                        Text(
                            text = data.windSpeed.toString(),
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
}


@Composable
fun LocationCard(state: WeatherState) {

    state.weatherData?.let { data ->
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
                            text = data.name,
                            color = colorResource(id = R.color.primary_text_color),
                            fontSize = dimensionResource(id = R.dimen.label_text_size).value.sp,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Text(
                            text = data.country,
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
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
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
                            text = getUnixTime(data.sunrise),
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
                            text = getUnixTime(data.sunset),
                            color = colorResource(id = R.color.secondary_text_color),
                            fontSize = dimensionResource(id = R.dimen.value_text_size).value.sp
                        )
                    }
                }
            }
        }
    }
}








