package com.example.vkcourse.applist.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vkcourse.applist.data.CatalogApp

@Composable
fun AppDetailsScreen(
    app: CatalogApp,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onInstallClick: () -> Unit,
    onShowcaseAppClick: (CatalogApp) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("details_content")
            .verticalScroll(rememberScrollState())
    ) {
        AppDetailsContent(
            app = app,
            onBackClick = onBackClick,
            onShareClick = onShareClick,
            onInstallClick = onInstallClick,
            onShowcaseAppClick = onShowcaseAppClick
        )
    }
}

@Composable
private fun AppDetailsContent(
    app: CatalogApp,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onInstallClick: () -> Unit,
    onShowcaseAppClick: (CatalogApp) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        DetailsToolbar(onBackClick = onBackClick, onShareClick = onShareClick)
        Spacer(modifier = Modifier.height(8.dp))
        AppDetailsHeader(app)
        Spacer(modifier = Modifier.height(16.dp))
        InstallButton(onClick = onInstallClick)
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        AppDescription(description = app.description, appId = app.id)
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        Developer(app = app)
        Spacer(modifier = Modifier.height(28.dp))
        ShowcasePager(onAppClick = onShowcaseAppClick)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DetailsToolbar(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = onBackClick) { Text("Назад") }
        TextButton(onClick = onShareClick) { Text("Поделиться") }
    }
}

@Composable
private fun AppDetailsHeader(app: CatalogApp) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(app.iconRes),
            contentDescription = "Иконка приложения ${app.name}",
            modifier = Modifier.size(112.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = app.category,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = app.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("VK Education", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                    Text("0+", modifier = Modifier.fillMaxWidth())
                    Text(
                        "Возраст",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Бесплатно")
                    Text(
                        "Стоимость",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun InstallButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Установить")
    }
}

@Composable
private fun AppDescription(description: String, appId: String) {
    Text(
        text = "Описание",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(8.dp))
    var descriptionExpanded by rememberSaveable(appId) {
        mutableStateOf(false)
    }
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = if (descriptionExpanded) Int.MAX_VALUE else 1
    )
    TextButton(onClick = { descriptionExpanded = !descriptionExpanded }) {
        Text(if (descriptionExpanded) "Свернуть" else "Читать далее")
    }
}

@Composable
private fun Developer(app: CatalogApp) {
    Column {
        Text(
            text = "VK Education",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "Разработчик • ${app.category}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun UnknownAppScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Приложение не найдено")
        TextButton(onClick = onBackClick) {
            Text("Вернуться в каталог")
        }
    }
}
