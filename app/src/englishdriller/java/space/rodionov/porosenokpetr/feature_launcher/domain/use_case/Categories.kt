package space.rodionov.porosenokpetr.feature_launcher.domain.use_case

import space.rodionov.porosenokpetr.core.domain.model.Category

object Categories {
    val englishCategories = listOf(
        Category("At home", nameRus = "Дом и хозяйство"),
        Category("Food", nameRus = "Жратва"),
        Category("Clothes", nameRus = "Шмотки"),
        Category("City", nameRus = "Город и транспорт"),
        Category("Work and business", nameRus = "Бизнес, работа, карьера"),
        Category("Science and technology", nameRus = "Образование),
        Category("Socium", nameRus = "Социум и взаимоотношения"),
        Category("Numbers", nameRus = "Числа"),
        Category("Health", nameRus = "Здоровье"),
        Category("Nature", nameRus = "Природа"),
        Category("Verbs 1", nameRus = "Глаголы 1"),
        Category("Verbs 2 (irregular)", nameRus = "Глаголы 2 (неправильные)"),
        Category("Verbs 3", nameRus = "Глаголы 3"),
        Category("Phrases 1", nameRus = "Фразочки 1"),
        Category("Phrases 2", nameRus = "Фразочки 2"),
        Category("Phrases 3", nameRus = "Фразочки 3"),
        Category("Phrases 4", nameRus = "Фразочки 4"),
    )
}