package com.example.codelabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Smth(
    val pic : Int,
    val text : String
)
//so whats the point
class DataSource(){
    fun LoadSmth() : List<Smth>{
        return listOf(
            Smth(R.drawable.ic_launcher_background,"text1"),
            Smth(R.drawable.ic_launcher_background,"text2"),
            Smth(R.drawable.ic_launcher_background,"text3"),
            Smth(R.drawable.ic_launcher_background,"text4"),
            Smth(R.drawable.ic_launcher_background,"text5"),
            Smth(R.drawable.ic_launcher_background,"text6")
            ,Smth(R.drawable.ic_launcher_background,"text7")

        )
    }
}

@Composable
fun SmthApp(){
     SmthList(smthList = DataSource().LoadSmth())
}

@Composable
fun SmthCard(smth: Smth, modifier: Modifier = Modifier){
    Card(modifier = modifier) {
        Column {
            Image(painter = painterResource(smth.pic), contentDescription = null,
                modifier = Modifier.fillMaxWidth().size(150.dp),
                contentScale = ContentScale.Crop)
            Text(text = smth.text, fontSize = 16.sp)
        }
    }

}

@Composable
fun SmthList(smthList: List<Smth>, modifier: Modifier=Modifier ){
    LazyColumn(modifier = modifier) {
        items(smthList){i ->
            SmthCard(smth = i,modifier)
        }
    }
}