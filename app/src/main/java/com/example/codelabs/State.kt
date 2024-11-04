package com.example.codelabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat

@Composable
fun CalculateTip() {
    //  var userInput by remember { mutableStateOf("") }

    var input by remember { mutableStateOf("") }
    //when we add remember we dont need value?
    val amount = input.toDoubleOrNull() ?: 0.0
    //toDoubleOrNull parses string to double
    val tip = calculateTip(amount)

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(start = 40.dp, end = 40.dp),
        verticalArrangement = Arrangement.Center) {

        Text(text = "Calculate Tip")
//        TextField(value=userInput,
//            onValueChange = {userInput = it})

        Calculate(modifier = Modifier.fillMaxWidth(), value = input, onValueChange = {input=it})
        Row(){
            Text(text ="Tip amount: ", fontSize = 30.sp)
            Text(text = tip, fontSize = 30.sp)

        }


    }

}

@Composable
fun Calculate(modifier: Modifier, value: String, onValueChange : (String) ->Unit) {
    TextField(
        value = value,//vallue is the property that reads state
        onValueChange = onValueChange,//here it holds value of var that changed
        modifier = modifier,
        label = { Text(text = "enter tip amount") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview
@Composable
fun Pre(){
    CalculateTip()
}