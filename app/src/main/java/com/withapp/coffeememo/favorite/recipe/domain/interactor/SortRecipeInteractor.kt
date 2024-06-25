package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.domain.use_case.SortRecipeUseCase
import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel
import javax.inject.Inject

class SortRecipeInteractor @Inject constructor()
    : SortRecipeUseCase {
    override fun handle(
        sortType: RecipeSortType,
        list: List<FavoriteRecipeModel>
    ): List<FavoriteRecipeModel> {
        val result = when(sortType) {
            RecipeSortType.OLD -> {
                list.sortedBy { recipe -> recipe.id}
            }
            RecipeSortType.NEW -> {
                list.sortedByDescending { recipe -> recipe.id }
            }
            RecipeSortType.ROAST -> {
                list.sortedByDescending { recipe -> recipe.roast}
            }
            RecipeSortType.GRIND_SIZE ->  {
                list.sortedByDescending { recipe -> recipe.grindSize }
            }
            RecipeSortType.RATING ->  {
                list.sortedByDescending { recipe -> recipe.rating }
            }
        }

        return result
    }
}