package com.example.areader.screens.stats


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.areader.components.ReaderAppBar
import com.example.areader.model.Item
import com.example.areader.model.MBook
import com.example.areader.navigation.ReaderScreens
import com.example.areader.screens.home.HomeScreenViewModel
import com.example.areader.screens.search.Bookrow
import com.example.areader.utils.formatDate
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderStatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Stats",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }, content = {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            books = if (!viewModel.data.value.data.isNullOrEmpty()) {
                viewModel.data.value.data!!.filter { mbook ->
                    (mbook.userId == currentUser?.uid)
                }
            } else {
                emptyList()
            }
            Column {

                Row {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .padding(2.dp)
                    ) {
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = "icon")
                    }
                    Text(
                        text = "hi, ${
                            currentUser?.email.toString()
                                .split("@")[0].uppercase(Locale.getDefault())
                        }"
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    val readBooksList: List<MBook> =
                        if (!viewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }
                        } else {
                            emptyList()
                        }
                    val readingbooks = books.filter { mBook ->
                        (mBook.startedReading != null && mBook.finishedReading == null)
                    }

                    Column(
                        modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Your Stats", style = MaterialTheme.typography.titleSmall)
                        Divider()
                        Text(text = "You're reading: ${readingbooks.size} books")
                        Text(text = "You're read: ${readBooksList.size} books")
                    }

                }
                if (viewModel.data.value.loading == true) {
                    LinearProgressIndicator()
                } else {
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)){
                        val readBooks :List<MBook> = if (!viewModel.data.value.data.isNullOrEmpty()){
                            viewModel.data.value.data!!.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }
                        }else{
                            emptyList()
                        }
                        items (items= readBooks){book ->
                       BookrowStats(book= book)
                        }
                    }
                }
            }
        }
    })
}


@Composable
fun BookrowStats(book:MBook) {
    Card(modifier = Modifier
        .clickable {
            //  navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(6.dp)) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top) {
            val imageUrl: String = if (book.photoUrl.toString().isEmpty())
                "http://books.google.com/books/content?id=onhDnwEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api"
            else {
                book.photoUrl.toString()
            }
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(70.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween){
                    
                }
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                if(book.rating!! >= 4){
                    Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                    Icon(imageVector = Icons.Default.ThumbUp, contentDescription = "Thumbs up",tint = Color.Green.copy(alpha = 0.5f))
                }else{
                    Box{}
                }
                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = "Started: ${formatDate(book.startedReading!!)}",
                    softWrap=true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = "Finished ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleSmall
                )


            }

        }

    }
}



