package dev.taleroangel.timetonic.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.taleroangel.timetonic.R
import dev.taleroangel.timetonic.domain.entities.Book
import dev.taleroangel.timetonic.presentation.ui.theme.TimetonicApplicationTheme
import io.bloco.faker.Faker

@Composable
fun BookItem(modifier: Modifier = Modifier, book: Book) {
    Box(modifier = modifier) {
        OutlinedCard {
            Image(
                painterResource(id = R.drawable.cover),
                contentDescription = stringResource(id = R.string.book_cover),
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 17.sp, fontWeight = FontWeight.W600, lineHeight = 20.sp
                    ),
                )
                if (book.description != null)
                    Text(
                        modifier = Modifier.padding(top = 6.dp),
                        text = book.description, maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                    )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(20)
                )
        ) {
            if (book.favorite)
                Icon(
                    Icons.Rounded.Star,
                    contentDescription = stringResource(id = R.string.book_favorite),
                )
            if (book.archived)
                Icon(
                    Icons.Rounded.Archive,
                    contentDescription = stringResource(id = R.string.book_archived),
                )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun BookItemLightPreview() {
    // Generate fake data
    val faker = Faker()

    TimetonicApplicationTheme {
        Surface {
            BookItem(
                modifier = Modifier.padding(16.dp),
                book = Book(
                    archived = true,
                    favorite = true,
                    title = faker.lorem.sentence(),
                    description = faker.lorem.paragraph()
                ),
            )
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun BookItemDarkPreview() {
    // Generate fake data
    val faker = Faker()

    TimetonicApplicationTheme {
        Surface {
            BookItem(
                modifier = Modifier.padding(16.dp),
                book = Book(
                    archived = true,
                    favorite = true,
                    title = faker.lorem.sentence(),
                    description = faker.lorem.paragraph()
                ),
            )
        }
    }
}