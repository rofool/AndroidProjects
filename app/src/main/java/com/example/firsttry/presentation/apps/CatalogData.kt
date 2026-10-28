package com.example.firsttry.presentation.apps


object CatalogData {
    fun getApps(): List<CatalogApp> = listOf(
        CatalogApp("chat", "Диалог", "Общайтесь с друзьями и делитесь важными моментами.", "Общение", AppIcon.CHAT),
        CatalogApp("music", "Ритм", "Любимая музыка и плейлисты на каждый день.", "Музыка", AppIcon.MUSIC),
        CatalogApp("books", "Книжная полка", "Читайте книги и сохраняйте интересные истории.", "Книги", AppIcon.BOOK),
        CatalogApp("travel", "Маршрут", "Находите новые места и планируйте путешествия.", "Путешествия", AppIcon.TRAVEL),
        CatalogApp("notes", "Заметки", "Записывайте идеи и составляйте списки дел.", "Продуктивность", AppIcon.NOTES),
        CatalogApp("photo", "Фокус", "Сохраняйте лучшие кадры и собирайте фотоальбомы.", "Фотография", AppIcon.PHOTO),
        CatalogApp("learn", "Курс", "Изучайте новое с помощью коротких ежедневных уроков.", "Образование", AppIcon.BOOK),
        CatalogApp("podcast", "На волне", "Слушайте подкасты о науке, культуре и технологиях.", "Развлечения", AppIcon.MUSIC),
    )
}
