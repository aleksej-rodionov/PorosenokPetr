package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.rodionov.porosenokpetr.core.domain.model.Category

@Entity
data class CategoryEntity(
    val name: String,
    val isCategoryActive: Boolean = true,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null
)

val swedishCategories = listOf(
    Category("At home", nameRus = "Дом и хозяйство", nameEng = "At home", nameUkr = "Вдома"),
    Category("Food", nameRus = "Жратва", nameEng = "Food", nameUkr = "Їжа"),
    Category("Clothes", nameRus = "Шмотки", nameEng = "Clothes", nameUkr = "Одяг"),
    Category("City", nameRus = "Город и транспорт", nameEng = "City", nameUkr = "Місто"),
    Category("Work and business", nameRus = "Бизнес, работа, карьера", nameEng = "Work and business", nameUkr = "Робота та бізнес"),
    Category("Science and technology", nameRus = "Образование, наука и технологии", nameEng = "Science and technology", nameUkr = "Наука і технології"),
    Category("Socium", nameRus = "Социум и взаимоотношения", nameEng = "Socium", nameUkr = "Соціум"),
    Category("Numbers", nameRus = "Числа", nameEng = "Numbers", nameUkr = "Номери"),
    Category("Health", nameRus = "Здоровье", nameEng = "Health", nameUkr = "Здоров\'я"),
    Category("Nature", nameRus = "Природа", nameEng = "Nature", nameUkr = "Природа"),
    Category("Verbs 1", nameRus = "Глаголы 1", nameEng = "Verbs 1", nameUkr = "Дієслова 1"),
    Category("Verbs 2 (irregular)", nameRus = "Глаголы 2 (неправильные)", nameEng = "Verbs 2 (irregular)", nameUkr = "Дієслова 2 (нерегулярні)"),
    Category("Verbs 3", nameRus = "Глаголы 3", nameEng = "Verbs 3", nameUkr = "Дієслова 3"),
    Category("Phrases 1", nameRus = "Фразочки 1", nameEng = "Phrases 1", nameUkr = "Фрази 1"),
    Category("Phrases 2", nameRus = "Фразочки 2", nameEng = "Phrases 2", nameUkr = "Фрази 2"),
    Category("Phrases 3", nameRus = "Фразочки 3", nameEng = "Phrases 3", nameUkr = "Фрази 3"),
    Category("Phrases 4", nameRus = "Фразочки 4", nameEng = "Phrases 4", nameUkr = "Фрази 4"),
)
