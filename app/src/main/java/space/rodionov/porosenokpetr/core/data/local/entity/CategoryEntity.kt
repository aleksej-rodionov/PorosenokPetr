package space.rodionov.porosenokpetr.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import space.rodionov.porosenokpetr.core.domain.model.Category

@Entity
data class CategoryEntity(
    val resourceName: String,
    val isCategoryActive: Boolean = true,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val nameRus: String,
    val nameUkr: String,
    val nameEng: String? = null
) {
    fun toCategory(): Category {
        return Category(
            resourceName = resourceName,
            isCategoryActive = isCategoryActive,
            nameRus = nameRus,
            nameUkr = nameUkr,
            nameEng = nameEng
        )
    }
}

val swedishCategories = listOf<CategoryEntity>(
    CategoryEntity("At home", nameRus = "Дом и хозяйство", nameEng = "At home", nameUkr = "Вдома"),
    CategoryEntity("Food", nameRus = "Жратва", nameEng = "Food", nameUkr = "Їжа"),
    CategoryEntity("Clothes", nameRus = "Шмотки", nameEng = "Clothes", nameUkr = "Одяг"),
    CategoryEntity("City", nameRus = "Город и транспорт", nameEng = "City", nameUkr = "Місто"),
    CategoryEntity("Work and business", nameRus = "Бизнес, работа, карьера", nameEng = "Work and business", nameUkr = "Робота та бізнес"),
    CategoryEntity("Science and technology", nameRus = "Образование, наука и технологии", nameEng = "Science and technology", nameUkr = "Наука і технології"),
    CategoryEntity("Socium", nameRus = "Социум и взаимоотношения", nameEng = "Socium", nameUkr = "Соціум"),
    CategoryEntity("Numbers", nameRus = "Числа", nameEng = "Numbers", nameUkr = "Номери"),
    CategoryEntity("Health", nameRus = "Здоровье", nameEng = "Health", nameUkr = "Здоров\'я"),
    CategoryEntity("Nature", nameRus = "Природа", nameEng = "Nature", nameUkr = "Природа"),
    CategoryEntity("Verbs 1", nameRus = "Глаголы 1", nameEng = "Verbs 1", nameUkr = "Дієслова 1"),
    CategoryEntity("Verbs 2 (irregular)", nameRus = "Глаголы 2 (неправильные)", nameEng = "Verbs 2 (irregular)", nameUkr = "Дієслова 2 (нерегулярні)"),
    CategoryEntity("Verbs 3", nameRus = "Глаголы 3", nameEng = "Verbs 3", nameUkr = "Дієслова 3"),
    CategoryEntity("Phrases 1", nameRus = "Фразочки 1", nameEng = "Phrases 1", nameUkr = "Фрази 1"),
    CategoryEntity("Phrases 2", nameRus = "Фразочки 2", nameEng = "Phrases 2", nameUkr = "Фрази 2"),
    CategoryEntity("Phrases 3", nameRus = "Фразочки 3", nameEng = "Phrases 3", nameUkr = "Фрази 3"),
    CategoryEntity("Phrases 4", nameRus = "Фразочки 4", nameEng = "Phrases 4", nameUkr = "Фрази 4"),
)
