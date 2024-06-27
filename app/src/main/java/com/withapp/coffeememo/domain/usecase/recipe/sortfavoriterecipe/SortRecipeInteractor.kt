package com.withapp.coffeememo.domain.usecase.recipe.sortfavoriterecipe

import com.withapp.coffeememo.presentation.favorite.recipe.model.FavoriteRecipeModel
import com.withapp.coffeememo.domain.model.recipe.RecipeSortType
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
            else -> listOf()
        }

        return result
    }
}