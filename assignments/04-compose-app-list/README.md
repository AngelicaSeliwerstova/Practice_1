# Практическая работа №4 — создание UI в Android

[← Все задания курса](../../README.md)

Экран каталога приложений и карточка приложения на Kotlin и Jetpack Compose. Данные и иконки локальные; интернет не нужен.

## Часть 1 — каталог

Синий тулбар и вертикальный список приложений. Каждая строка содержит иконку, название, краткое описание и категорию.

## Часть 2 — карточка и витрина

Нажатие на приложение открывает карточку. В её нижней части расположен блок «Витрина»: горизонтальный постраничный `HorizontalPager` с тремя страницами по три приложения. Для строк каталога и витрины используется один Compose-компонент `AppListItem`. Нажатие на приложение из витрины возвращает в каталог.

Навигация реализована с помощью Navigation Compose (`NavHost`, `composable`, `NavController`). Карточка повторяет структуру готового экрана подробностей из [практического репозитория VK Education](https://github.com/maltsevmicyou/VkEducation): тулбар, заголовок приложения, установка, описание и разработчик; блок «Витрина» добавлен внизу.

## Практическая работа №5 — presentation

Каталог использует `CatalogViewModel` для хранения списка и одноразовых UI-событий. Сообщение Snackbar отправляется по нажатию на тулбар через поток событий без replay: после поворота экрана оно не показывается повторно. ViewModel и сохранённый Navigation back stack удерживают состояние при пересоздании Activity.

## Практическая работа №6 — Clean Architecture

Каталог разделён на три слоя. `presentation` содержит Compose UI и `CatalogViewModel`; ViewModel получает список через `GetCatalogAppsUseCase`. В `domain` расположены независимая от Android модель `CatalogApp`, интерфейс `CatalogRepository` и use case. В `data` находятся DTO, локальный источник захардкоженных данных, mapper `CatalogAppDto.toDomain()` и реализация репозитория `DefaultCatalogRepository`. UI-ресурсы иконок сопоставляются с доменным enum только в presentation UI.

```text
data/
├── CatalogAppDto.kt
├── CatalogAppMapper.kt
├── CatalogDataSource.kt
└── DefaultCatalogRepository.kt
domain/
├── model/CatalogApp.kt
├── repository/CatalogRepository.kt
└── usecase/GetCatalogAppsUseCase.kt
presentation/
└── CatalogViewModel.kt
ui/
└── Compose screens
```

Unit-тест проверяет преобразование DTO в domain-модель и получение девяти приложений через repository/use case.

## Условия и реализация

| Требование | Реализация |
| --- | --- |
| Тулбар с иконкой приложения слева | `TopAppBar` с локальным векторным ресурсом |
| Акцентный цвет тулбара | Синяя тема приложения |
| Вертикальный список | `LazyColumn` с ключами элементов |
| Поля каждой записи | Иконка, название, описание и категория |
| Jetpack Compose | Весь экран собран Compose-компонентами |
| Захардкоженные данные | DTO из `CatalogDataSource` и локальные drawable-иконки |
| Витрина в карточке | Три страницы `HorizontalPager`, по три приложения |
| Навигация | `NavHost` и `NavController` |
| Presentation-слой | `CatalogViewModel` и одноразовый поток UI-событий |
| Data, domain, presentation | DTO → mapper → domain model; repository и use case; ViewModel и Compose UI |

Поддерживаются светлая и тёмная системные темы. Список сохраняет позицию прокрутки, карточки подстраиваются под текст, а Snackbar не повторяется после пересоздания Activity.

## Запуск и проверка

Открой корень репозитория в Android Studio и выбери модуль `homework-04`.

Из корня проекта в PowerShell:

```powershell
.\gradlew.bat :homework-04:assembleDebug
.\gradlew.bat :homework-04:testDebugUnitTest
.\gradlew.bat :homework-04:lintDebug
```

Для UI-тестов нужен запущенный эмулятор или подключённое устройство:

```powershell
.\gradlew.bat :homework-04:connectedDebugAndroidTest
```

## Самопроверка

- [ ] При запуске виден синий тулбар с иконкой слева.
- [ ] У всех девяти приложений есть иконка, название, описание и категория.
- [ ] Список прокручивается до последнего приложения, а тулбар остаётся сверху.
- [ ] Нажатие на приложение открывает карточку, а на элемент витрины возвращает в каталог.
- [ ] Внизу карточки отображается витрина из трёх страниц по три приложения.
- [ ] Нажатие на тулбар показывает Snackbar; после поворота Snackbar не появляется заново.
- [ ] Содержимое читается в светлой и тёмной темах.
- [ ] При увеличении системного шрифта строки карточек растут по высоте.
- [ ] Каталог работает без интернета.

## Структура задания

```text
src/main/java/com/example/vkcourse/applist/
├── data/CatalogAppDto.kt
├── data/CatalogAppMapper.kt
├── data/CatalogDataSource.kt
├── data/DefaultCatalogRepository.kt
├── domain/model/CatalogApp.kt
├── domain/repository/CatalogRepository.kt
├── domain/usecase/GetCatalogAppsUseCase.kt
├── presentation/CatalogViewModel.kt
├── ui/AppCatalogApp.kt
├── ui/AppCatalogScreen.kt
├── ui/AppDetailsScreen.kt
├── ui/ShowcasePager.kt
├── ui/theme/Theme.kt
└── MainActivity.kt
src/main/res/drawable/  # Иконки каталога и приложений
src/test/               # Проверки состава витрины
src/androidTest/        # Проверки навигации, витрины и Snackbar
```
