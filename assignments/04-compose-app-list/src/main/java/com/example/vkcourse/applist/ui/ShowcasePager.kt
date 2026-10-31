package com.example.vkcourse.applist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vkcourse.applist.domain.model.CatalogApp
import com.example.vkcourse.applist.domain.model.SHOWCASE_PAGE_COUNT
import com.example.vkcourse.applist.domain.model.SHOWCASE_PAGE_SIZE

@Composable
fun ShowcasePager(
    apps: List<CatalogApp>,
    onAppClick: (CatalogApp) -> Unit,
    modifier: Modifier = Modifier
) {
    val pages = apps.chunked(SHOWCASE_PAGE_SIZE)
    require(pages.size == SHOWCASE_PAGE_COUNT && pages.all { it.size == SHOWCASE_PAGE_SIZE }) {
        "В витрине должно быть ровно три страницы по три приложения"
    }
    val pagerState = rememberPagerState(pageCount = { SHOWCASE_PAGE_COUNT })

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Витрина",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
                .testTag("showcase_pager"),
            key = { page -> page }
        ) { page ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pages[page].forEach { app ->
                    AppListItem(
                        app = app,
                        onClick = { onAppClick(app) }
                    )
                }
            }
        }
        Text(
            text = "${pagerState.currentPage + 1} / $SHOWCASE_PAGE_COUNT",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
