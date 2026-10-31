package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.R

data class CatalogApp(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconRes: Int
)

val sampleApps = listOf(
    CatalogApp(
        id = "vk",
        name = "ВКонтакте",
        description = "Друзья, сообщества и истории о том, что вам близко.",
        category = "Социальные сети",
        iconRes = R.drawable.ic_app_social
    ),
    CatalogApp(
        id = "music",
        name = "VK Музыка",
        description = "Любимые треки и новые плейлисты под настроение.",
        category = "Музыка",
        iconRes = R.drawable.ic_app_music
    ),
    CatalogApp(
        id = "video",
        name = "VK Видео",
        description = "Видео, шоу и трансляции любимых авторов.",
        category = "Видео",
        iconRes = R.drawable.ic_app_video
    ),
    CatalogApp(
        id = "mail",
        name = "Почта Mail",
        description = "Личные и рабочие письма в одном месте.",
        category = "Производительность",
        iconRes = R.drawable.ic_app_mail
    ),
    CatalogApp(
        id = "cloud",
        name = "Облако Mail",
        description = "Фотографии и документы всегда под рукой.",
        category = "Производительность",
        iconRes = R.drawable.ic_app_cloud
    ),
    CatalogApp(
        id = "education",
        name = "VK Образование",
        description = "Курсы и полезные материалы для учёбы.",
        category = "Образование",
        iconRes = R.drawable.ic_app_education
    ),
    CatalogApp(
        id = "games",
        name = "VK Игры",
        description = "Игры для коротких перерывов и больших побед.",
        category = "Игры",
        iconRes = R.drawable.ic_app_games
    ),
    CatalogApp(
        id = "market",
        name = "VK Маркет",
        description = "Вещи для дома, подарки и интересные находки.",
        category = "Покупки",
        iconRes = R.drawable.ic_app_market
    )
)
