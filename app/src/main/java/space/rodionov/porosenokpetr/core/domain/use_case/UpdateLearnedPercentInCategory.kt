package space.rodionov.porosenokpetr.core.domain.use_case

import space.rodionov.porosenokpetr.core.domain.repository.WordRepo
import space.rodionov.porosenokpetr.core.util.countPercentage

class UpdateLearnedPercentInCategory(
    private val repo: WordRepo
) {

    suspend operator fun invoke(catName: String) {

        val words = repo.getWordsByCat(catName)
        val learnedFromActivePercent = words.countPercentage()
        repo.updateLearnedPercentInCategory(catName, learnedFromActivePercent)
    }
}