package com.example.submissioncompose.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Call
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.submissioncompose.R
import com.example.submissioncompose.component.animation.AnimatingFabContent
import com.example.submissioncompose.di.Injection
import com.example.submissioncompose.model.Account2
import com.example.submissioncompose.ui.common.UiState
import com.example.submissioncompose.ui.common.UiStateBookmarked
import com.example.submissioncompose.ui.factory.ViewModelFactory
import com.example.submissioncompose.ui.theme.Purple500

@Composable
fun DetailScreen(
    detailId: String,
    viewModel: DetailScreenViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAccountById(detailId)
            }
            is UiState.Success -> {
                val data = uiState.data
                viewModel.uiStateBookmarked.collectAsState(initial = UiStateBookmarked.NothingHappen).value.let { uiStateBookmarked ->
                    when (uiStateBookmarked) {
                        is UiStateBookmarked.NothingHappen -> {
                            DetailContent(
                                data.accountImageId,
                                data.name,
                                data.sex,
                                data.age,
                                data.description,
                                data.email,
                                navigateBack,
                                data.id,
                                onBookmarkClicked = { account ->
                                    viewModel.addAccount(account)
                                },
                                false,
                            )
                        }
                        is UiStateBookmarked.SuccessBookmarked -> {
                            val bookmarked = uiStateBookmarked.dataBookmarked

                            DetailContent(
                                data.accountImageId,
                                data.name,
                                data.sex,
                                data.age,
                                data.description,
                                data.email,
                                navigateBack,
                                data.id,
                                onBookmarkClicked = { account ->
                                    viewModel.addAccount(account)
                                },
                                bookmarked,
                                )
                        }
                    }
                }

            }
            is UiState.Error -> {}
        }
    }
}

@Composable
private fun DetailContent(
    @DrawableRes image: Int,
    nama: String,
    sex: String,
    age: Int,
    description: String,
    email: String,
    onBackClick: () -> Unit,
    id: String,
    onBookmarkClicked: (account: Account2) -> Unit,
    isBookmarked: Boolean
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
                        image,
                        onBackClick,
                        onBookmarkClicked,
                        nama,
                        sex,
                        age,
                        email,
                        description,
                        id,
                        isBookmarked
                    )
                    ProfileContent(
                        nama,
                        sex,
                        age,
                        description,
                        email,
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
    onBackClick: () -> Unit,
    onBookmarkClicked: (account: Account2) -> Unit,
    nama: String,
    sex: String,
    age: Int,
    email: String,
    description: String,
    id: String,
    isBookmarked: Boolean
) {
    val offset = (scrollState.value / 2)
    val offsetDp = with(LocalDensity.current) { offset.toDp() }

    var iconFav: ImageVector = if (isBookmarked) {
        Icons.Default.Favorite
    } else Icons.Default.FavoriteBorder

    var contentFav: String = if (isBookmarked) {
        stringResource(id = R.string.favorite)
    } else {
        stringResource(id = R.string.no_favorite)
    }

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
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.back),
            modifier = Modifier
                .padding(16.dp)
                .clickable { onBackClick() }
        )
        Icon(
            imageVector = iconFav,
            contentDescription = contentFav,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable {
                    onBookmarkClicked(
                        Account2(
                            id,
                            nama,
                            sex,
                            age,
                            description,
                            email,
                            image
                        )
                    )
                }
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
