package com.example.vkcourse.applist.data

import com.example.vkcourse.applist.domain.model.CatalogAppIcon

class CatalogDataSource {
    fun getApps(): List<CatalogAppDto> = listOf(
        CatalogAppDto(
            id = "vk",
            name = "ВКонтакте",
            description = "Друзья, сообщества и истории о том, что вам близко.",
            category = "Социальные сети",
            icon = CatalogAppIcon.SOCIAL
        ),
        CatalogAppDto(
            id = "music",
            name = "VK Музыка",
            description = "Любимые треки и новые плейлисты под настроение.",
            category = "Музыка",
            icon = CatalogAppIcon.MUSIC
        ),
        CatalogAppDto(
            id = "video",
            name = "VK Видео",
            description = "Видео, шоу и трансляции любимых авторов.",
            category = "Видео",
            icon = CatalogAppIcon.VIDEO
        ),
        CatalogAppDto(
            id = "mail",
            name = "Почта Mail",
            description = "Личные и рабочие письма в одном месте.",
            category = "Производительность",
            icon = CatalogAppIcon.MAIL
        ),
        CatalogAppDto(
            id = "cloud",
            name = "Облако Mail",
            description = "Фотографии и документы всегда под рукой.",
            category = "Производительность",
            icon = CatalogAppIcon.CLOUD
        ),
        CatalogAppDto(
            id = "education",
            name = "VK Образование",
            description = "Курсы и полезные материалы для учёбы.",
            category = "Образование",
            icon = CatalogAppIcon.EDUCATION
        ),
        CatalogAppDto(
            id = "games",
            name = "VK Игры",
            description = "Игры для коротких перерывов и больших побед.",
            category = "Игры",
            icon = CatalogAppIcon.GAMES
        ),
        CatalogAppDto(
            id = "market",
            name = "VK Маркет",
            description = "Вещи для дома, подарки и интересные находки.",
            category = "Покупки",
            icon = CatalogAppIcon.MARKET
        ),
        CatalogAppDto(
            id = "calls",
            name = "VK Звонки",
            description = "Звонки друзьям и коллегам в хорошем качестве.",
            category = "Общение",
            icon = CatalogAppIcon.CALLS
        )
    )
}
