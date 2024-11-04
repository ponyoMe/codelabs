package com.example.codelabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.IOException
import androidx.lifecycle.viewmodel.compose.viewModel



//idk does not work but kinda correct tho

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"
private val retrofit=Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


@Serializable
data class MarsPhoto(
    val id:String,
    @SerialName("img_src")
    val imgSrc:String
)

interface MarsApiService{
    @GET("photos")
    suspend fun getPhotos() : List<MarsPhoto>

}

object MarsApi{
    val retrofitService : MarsApiService by lazy{
        retrofit.create(MarsApiService::class.java)
    }
}

sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}


class MarsViewModel : ViewModel(){
    var marsUiState:MarsUiState by mutableStateOf(MarsUiState.Loading)
         private set


    init {
        getMarsPhotos()
    }
   private fun getMarsPhotos(){
       viewModelScope.launch {
           try {
               val listResult=MarsApi.retrofitService.getPhotos()
               marsUiState=MarsUiState.Success(
                   "Success ${listResult.size}"
               )
           }catch (e:IOException){
               marsUiState=MarsUiState.Error
           }


       }

    }
}

@Composable
fun HomeScreen(marsUiState: MarsUiState, modifier: Modifier=Modifier){
    when(marsUiState){
        is MarsUiState.Success -> ResultScreen(marsUiState.photos,modifier=Modifier.fillMaxWidth())
        is MarsUiState.Error -> ErrorScreen(modifier=Modifier.fillMaxSize())
        is MarsUiState.Loading -> LoadingScreen(modifier=Modifier.fillMaxSize())
    }

}

@Composable
fun MarsApp(viewModel: MarsViewModel= viewModel()){
    HomeScreen(marsUiState = viewModel.marsUiState)
}

@Composable
fun ResultScreen(photos : String, modifier: Modifier=Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }

}

@Composable
fun LoadingScreen(modifier: Modifier=Modifier){
    Icon(imageVector = Icons.Filled.Refresh, contentDescription = null)

}

@Composable
fun ErrorScreen(modifier: Modifier=Modifier){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        Icon(imageVector = Icons.Filled.Info, contentDescription = null)
        Text(text = "Failed to load", modifier = Modifier.padding(16.dp))
    }

}