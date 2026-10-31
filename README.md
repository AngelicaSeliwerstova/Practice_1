# VK Education — Android-практики

Учебный Android-проект на Kotlin и Jetpack Compose. Каждая практическая работа находится в отдельном модуле, поэтому её можно собирать и запускать независимо.

## Задания

| № | Тема | Модуль | Описание |
| --- | --- | --- | --- |
| 1 | Исходное приложение из `Practice_1` | `homework-01` | [Исходники и запуск](assignments/01-practice/README.md) |
| 3 | Activity и Intent | `homework-03` | [Описание и самопроверка](assignments/03-intents/README.md) |
| 4 | Создание UI в Android. Части 1–2 | `homework-04` | [Каталог, карточка и витрина](assignments/04-compose-app-list/README.md) |
| 5 | Presentation-слой | `homework-04` | ViewModel и одноразовые UI-события описаны в README задания 4 |
| 6 | Слоистая архитектура (Clean Architecture) | `homework-04` | Data, domain, presentation, DTO, mapper, repository и use case описаны в README задания 4 |

## Структура проекта

```text
assignments/
├── 01-practice/            # Исходное приложение Practice_1
├── 03-intents/             # Практическая работа №3
└── 04-compose-app-list/    # Практическая работа №4
gradle/                     # Wrapper и версии зависимостей
build.gradle.kts            # Общие плагины Gradle
settings.gradle.kts         # Подключение модулей курса
```

## Требования

- Android Studio с Android SDK Platform 37.
- JDK 21 или новее (полный JDK с `jlink`).
- Эмулятор либо устройство с Android 7.0 (API 24) или новее.

## Сборка

Из корня репозитория в PowerShell:

```powershell
# Собрать каждую практическую работу отдельно
.\gradlew.bat :homework-01:assembleDebug
.\gradlew.bat :homework-03:assembleDebug
.\gradlew.bat :homework-04:assembleDebug

# Собрать оба приложения
.\gradlew.bat :homework-01:assembleDebug :homework-03:assembleDebug :homework-04:assembleDebug
```

## Проверки

```powershell
.\gradlew.bat :homework-01:testDebugUnitTest :homework-03:testDebugUnitTest :homework-04:testDebugUnitTest
.\gradlew.bat :homework-03:lintDebug :homework-04:lintDebug
```

UI-тесты навигации, витрины и Snackbar запускаются на подключённом Android-устройстве или эмуляторе:

```powershell
.\gradlew.bat :homework-04:connectedDebugAndroidTest
```

## Сдача

Для каждого нового задания создаётся отдельная ветка от `main`. Решение отправляется Pull Request в `main`; в описании PR указываются задание, выполненные требования и результаты проверок.
