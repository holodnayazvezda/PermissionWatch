# PermissionWatch

Android-приложение для просмотра установленных пакетов и подробной информации об их разрешениях. Интерфейс написан на Kotlin и Jetpack Compose.

## Сборка и запуск

Откройте корень проекта в Android Studio, установите Android SDK 36 и выполните синхронизацию Gradle. Используйте JDK, совместимый с версией Android Gradle Plugin проекта; настройки Java указаны в `app/build.gradle.kts`. Минимальная версия устройства — Android API 27.

Для сборки отладочного APK:

```sh
sh gradlew :app:assembleDebug
```

В Windows: `gradlew.bat :app:assembleDebug`. APK появится в `app/build/outputs/apk/debug/`. Для запуска выберите модуль `app` и устройство или эмулятор в Android Studio.

## Устройство проекта

Основные экраны — список пакетов и сведения о выбранном пакете. Код находится в `app/src/main/java/com/example/permission_watch/presentation/screens/`; модели — в `domain/models/`. Состав видимых пакетов зависит от ограничений версии Android и настроек манифеста.
