
package com.example.intentpractice

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    var text by rememberSaveable {
        mutableStateOf("")
    }

    var error by rememberSaveable {
        mutableStateOf(false)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        val contentModifier = if (maxWidth > 600.dp) {
            Modifier.width(480.dp)
        } else {
            Modifier.fillMaxWidth()
        }

        Column(
            modifier = contentModifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Практическая работа №3",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    error = false
                },
                label = {
                    Text("Введите текст или номер телефона")
                },
                isError = error,
                supportingText = {
                    if (error) {
                        Text("Проверьте введённые данные")
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (text.isBlank()) {
                        error = true
                    } else {
                        val intent = Intent(
                            context,
                            SecondActivity::class.java
                        )

                        intent.putExtra(
                            "message",
                            text.trim()
                        )

                        context.startActivity(intent)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Открыть вторую Activity")
            }

            Button(
                onClick = {
                    val phone = text.trim()

                    val validPhone =
                        Regex("""\+?[0-9()\s-]{3,20}""")
                            .matches(phone) &&
                                phone.count { it.isDigit() } >= 3

                    if (!validPhone) {
                        error = true
                    } else {
                        val intent = Intent(
                            Intent.ACTION_DIAL,
                            Uri.fromParts("tel", phone, null)
                        )

                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                "Приложение телефона не найдено",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Позвонить другу")
            }

            Button(
                onClick = {
                    if (text.isBlank()) {
                        error = true
                    } else {
                        val intent = Intent(
                            Intent.ACTION_SEND
                        ).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                text.trim()
                            )
                        }

                        val chooser = Intent.createChooser(
                            intent,
                            "Поделиться через"
                        )

                        try {
                            context.startActivity(chooser)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                "Не удалось открыть меню",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Поделиться текстом")
            }
        }
    }
}
