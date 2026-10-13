package com.example.firsttry

import com.example.firsttry.domain.appdetails.AppDetails
import com.example.firsttry.domain.appdetails.Category
import com.example.firsttry.domain.appdetails.SimilarApp

// Local demo values for the homework, not actual store metadata.
private val demoIcon = "android.resource://com.example.firsttry/${R.mipmap.ic_launcher}"

fun AppItem.asSimilarApp() = SimilarApp(id.toString(), name, demoIcon)

fun AppItem.asAppDetails() = AppDetails(
    id = id.toString(), name = name, developer = "VK",
    category = when (id) {
        2 -> Category.MUSIC
        3, 4 -> Category.VIDEO
        7 -> Category.GAME
        8 -> Category.PRODUCTIVITY
        9 -> Category.EDUCATION
        else -> Category.SOCIAL
    },
    ageRating = 12, size = 100f, iconUrl = demoIcon,
    screenshotUrlList = emptyList(),
    description = when (id) {
        1 -> "Общайтесь с друзьями, делитесь фотографиями и читайте новости любимых сообществ."
        2 -> "Слушайте музыку, собирайте плейлисты и открывайте для себя новых исполнителей."
        3 -> "Смотрите видео и прямые трансляции, сохраняйте понравившиеся ролики."
        4 -> "Открывайте короткие видео и делитесь собственными историями с подписчиками."
        5 -> "Переписывайтесь с друзьями, создавайте групповые чаты и отправляйте файлы."
        6 -> "Общайтесь по видеосвязи и собирайте участников на совместные встречи."
        7 -> "Открывайте игры, следите за игровыми новостями и находите единомышленников."
        8 -> "Храните файлы в облаке и получайте доступ к ним с разных устройств."
        9 -> "Изучайте технологии, проходите учебные курсы и практикуйтесь на проектах."
        else -> description
    },
)
