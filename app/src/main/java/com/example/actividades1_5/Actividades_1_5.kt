package com.example.actividades1_5

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
Actividad 1:
Hacer que el texto del botón muestre "Cargar perfil", pero cambie a "Cancelar"
cuando se muestre la línea de progreso... Cuando pulsemos "Cancelar" vuelve al texto por defecto.
*/
@Preview(showBackground = true)
@Composable
fun Actividad1() {
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var btnPadding by rememberSaveable { mutableStateOf(0) }
    Column(
        Modifier
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (showLoading) {
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 10.dp
            )
            LinearProgressIndicator(
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Red,
                trackColor = Color.LightGray
            )
        }

        Button(
            modifier = Modifier.padding(top = profileBtn(showLoading).dp),
            onClick = {
                showLoading = !showLoading
            }
        ) {
            if (showLoading) {
                Text(text = "Cancelar")
            } else Text(text = "Cargar perfil")
        }

    }
}

/*
Actividad 2:
Modifica ahora también que se separe el botón de la línea de progreso 30 dp,
pero usando un estado... es decir, cuando no sea visible no quiero que tenga la separación
aunque no se vea.
*/
fun profileBtn(showLoading: Boolean): Int {
    if (showLoading) {
        return 30
    } else return 0
}

/*
Actividad 3:
- Separar los botones entre ellos 10 dp, del indicador de progreso 15 dp y centrarlos horizontalmente.
- Cuando se clique el botón Incrementar, debe añadir 0.1 a la propiedad progress y quitar 0.1 cuando se pulse el botón Decrementar.
- Evitar que nos pasemos de los márgenes de su propiedad progressStatus (0-1)
*/
@Preview(showBackground = true)
@Composable
fun Actividad3() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var progresIndicator by rememberSaveable { mutableFloatStateOf(0f) }
        LinearProgressIndicator(progress = progresIndicator)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                if (progresIndicator + 0.1f <= 1f) {
                    progresIndicator += 0.1f
                } else progresIndicator = 1f
            }, Modifier.padding(top = 15.dp)) {
                Text(text = "Incrementar")
            }
            Button(onClick = {
                if (progresIndicator - 0.1f >= 0f) {
                    progresIndicator -= 0.1f
                } else progresIndicator = 0f
            }, Modifier.padding(top = 15.dp, start = 10.dp)) {
                Text(text = "Reducir")
            }
        }
    }
}


/*
Actividad 4:
Sitúa el TextField en el centro de la pantalla y haz que reemplace el valor de una coma por un punto
y que no deje escribir más de un punto decimal...
*/
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Actividad4() {
    var myVal by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = myVal,
            onValueChange = {
                var text = it.replace(",", ".")
                if (text.count { it -> it == '.' } <= 1) {
                    myVal = text
                }
            },
            label = { Text(text = "Importe") }
        )
    }
}


/*
Actividad 5:
Haz lo mismo, pero elevando el estado a una función superior y usando un componente OutlinedTextField
al que debes añadir un padding alrededor de 15 dp y establecer colores diferentes en los bordes
cuando tenga el foco y no lo tenga.
A nivel funcional no permitas que se introduzcan caracteres que invaliden un número decimal.
*/
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Actividad5() {
    var myVal by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier.padding(15.dp),
            value = myVal,
            onValueChange = { myVal = noDots(it) },
            label = { Text(text = "Importe") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Magenta
            )
        )
}
fun noDots(myVal: String): String{
    val regex = "^\\d+(\\.\\d+)?$".toRegex()
    var text = myVal
    var newText = myVal.replace(",", ".")
    if (newText.count { it -> it == '.' } <= 1 && regex.matches(newText)) {
        text = newText
    }
    return text
}
