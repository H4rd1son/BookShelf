package com.example.bookshelf.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.data.Book

/*прежде чем создавать функцию для отображения списка книг,
  нужно описать функцию для создания отдельного элемента */

@Composable
fun BooksGridScreens(
    books: List<Book>,
    modifier: Modifier,
    onBookClicked: (Book) -> Unit
) {
    LazyVerticalGrid(              //ленивая сетка, которая будет прогружаться по мере прокручивания
        columns = GridCells.Adaptive(150.dp), //сетка будет в 2 колонки
        contentPadding = PaddingValues(4.dp)  //отступ по краям списка
    ) {
        itemsIndexed(books) { _, book->
            BooksCard(book = book, modifier, onBookClicked)
        }
    }
}

@Composable
fun BooksCard(
    book: Book,
    modifier: Modifier = Modifier,
    onBookClicked: (Book) -> Unit
) {
    val ctx = LocalContext.current //context? for what
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .requiredHeight(296.dp)
            .clickable { onBookClicked(book) },
        elevation = 8.dp
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            book.title?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 4.dp, bottom = 8.dp)
                )
            }
            AsyncImage(
                modifier = modifier
                    .fillMaxWidth()
                    .requiredHeight(220.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageLink?.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_book_96),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = stringResource(id = R.string.content_description),
                contentScale = ContentScale.Crop
            )
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                onClick = { val urlIntent = Intent( //end setting this button
                Intent.ACTION_VIEW,
                Uri.parse("https://www.ya.ru")
            )
                ctx.startActivity(urlIntent) }) {
                Text(text = stringResource(id = R.string.buy))
            }
        }
    }
}