package com.example.submissioncompose.ui.screen.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.submissioncompose.R
import com.example.submissioncompose.component.animation.AnimatingFabContent
import com.example.submissioncompose.ui.theme.Purple500

@Composable
fun ProfileScreen() = DetailContent()


@Composable
private fun DetailContent(
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.weight(1f)) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    DetailHeader(
                        scrollState,
                        this@BoxWithConstraints.maxHeight,
                        R.drawable.cat
                    )
                    ProfileContent(
                        stringResource(id = R.string.my_name),
                        stringResource(id = R.string.my_sex),
                        stringResource(id = R.string.my_age).toInt(),
                        stringResource(id = R.string.my_description),
                        stringResource(id = R.string.my_email),
                        this@BoxWithConstraints.maxHeight
                    )
                }
            }
            AdoptFab(
                extended = scrollState.value == 0,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
private fun DetailHeader(
    scrollState: ScrollState,
    containerHeight: Dp,
    @DrawableRes image: Int,
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    Box {
        Image(
            modifier = Modifier
                .heightIn(max = containerHeight / 2)
                .fillMaxWidth()
                .padding(top = offsetDp),
            painter = painterResource(id = image),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
private fun ProfileContent(
    nama: String,
    sex: String,
    age: Int,
    description: String,
    email: String,
    containerHeight: Dp
) {
    Column {
        Spacer(modifier = Modifier.height(8.dp))

        Name(nama)

        ProfileProperty(stringResource(R.string.sex), sex)

        ProfileProperty(stringResource(R.string.age), age.toString())

        ProfileProperty(stringResource(R.string.email), email)

        ProfileProperty(stringResource(R.string.description), description)

        // Add a spacer that always shows part (320.dp) of the fields list regardless of the device,
        // in order to always leave some content at the top.
        Spacer(Modifier.height((containerHeight - 320.dp).coerceAtLeast(0.dp)))
    }
}

@Composable
private fun Name(
    nama: String
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Name(
            nama = nama,
            modifier = Modifier.align(Alignment.Start)
        )
    }
}

@Composable
private fun Name(nama: String, modifier: Modifier = Modifier) {
    Text(
        text = nama,
        modifier = modifier,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ProfileProperty(label: String, value: String, isLink: Boolean = false) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider()
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
            )
        }
        val style = if (isLink) {
            MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
        } else {
            MaterialTheme.typography.body1
        }
        Text(
            text = value,
            style = style
        )
    }
}

@Composable
fun AdoptFab(extended: Boolean, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { /* TODO */ },
        modifier = modifier
            .padding(16.dp)
            .padding()
            .height(48.dp)
            .widthIn(min = 48.dp),
        backgroundColor = Purple500,
        contentColor = Color.White
    ) {
        AnimatingFabContent(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = stringResource(R.string.hire_me)
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.hire_me),
                )
            },
            extended = extended

        )
    }
}