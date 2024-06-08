package dev.taleroangel.timetonic.presentation.ui.views.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.domain.entities.Book
import dev.taleroangel.timetonic.presentation.ui.components.BookItem
import dev.taleroangel.timetonic.presentation.ui.state.BooksViewState
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme
import io.bloco.faker.Faker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksView(
    state: BooksViewState,
    lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    onRefresh: () -> Unit = {}
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.books))
        }, actions = {
            IconButton(onClick = onRefresh) {
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = stringResource(id = R.string.refresh)
                )
            }
        })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                BooksViewState.Loading -> CircularProgressIndicator()
                is BooksViewState.Error -> Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    val errorVerticalScrollState = rememberScrollState()

                    Icon(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .size(50.dp),
                        imageVector = Icons.Rounded.Error,
                        contentDescription = stringResource(id = R.string.error),
                    )
                    Text(
                        text = stringResource(id = R.string.error),
                        style = MaterialTheme.typography.displaySmall,
                    )

                    Card(modifier = Modifier.padding(top = 16.dp)) {
                        Column(
                            Modifier
                                .padding(16.dp)
                                .verticalScroll(errorVerticalScrollState)
                        ) {
                            Text(text = state.err.toString())
                        }
                    }
                }


                is BooksViewState.Content -> {
                    if (state.books.isEmpty()) {
                        Text(
                            modifier = Modifier.padding(18.dp),
                            text = stringResource(id = R.string.books_empty),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                        )
                    } else {
                        LazyVerticalStaggeredGrid(
                            state = lazyStaggeredGridState,
                            columns = StaggeredGridCells.Fixed(2),
                            contentPadding = PaddingValues(all = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalItemSpacing = 8.dp,
                        ) {
                            items(state.books) { book ->
                                BookItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    book = book
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun BooksViewLightContentPreview() {
    // Fake data generator
    val faker = Faker()

    TimetonicApplicationTheme {
        BooksView(state = BooksViewState.Content(books = List(10) { _ ->
            Book(
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
                favorite = faker.bool.bool(),
                archived = faker.bool.bool(),
            )
        }))
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun BooksViewDarkContentPreview() {
    // Fake data generator
    val faker = Faker()

    TimetonicApplicationTheme {
        BooksView(state = BooksViewState.Content(books = List(10) { _ ->
            Book(
                title = faker.lorem.sentence(),
                description = faker.lorem.paragraph(),
                favorite = faker.bool.bool(),
                archived = faker.bool.bool(),
            )
        }))
    }
}

@Composable
@Preview
fun BooksViewEmptyContentPreview() {
    TimetonicApplicationTheme {
        BooksView(
            state = BooksViewState.Content(
                books = listOf()
            )
        )
    }
}

@Composable
@Preview
fun BooksViewLoadingPreview() {
    TimetonicApplicationTheme {
        BooksView(state = BooksViewState.Loading)
    }
}

@Composable
@Preview
fun BooksViewErrorPreview() {
    // Fake data generator
    val faker = Faker()

    TimetonicApplicationTheme {
        BooksView(
            state = BooksViewState.Error(
                Exception(faker.lorem.sentence())
            )
        )
    }
}

@Composable
@Preview
fun BooksViewScrollErrorPreview() {
    // Fake data generator
    val faker = Faker()

    TimetonicApplicationTheme {
        BooksView(
            state = BooksViewState.Error(
                Exception(faker.lorem.paragraph(sentenceCount = 50))
            )
        )
    }
}


