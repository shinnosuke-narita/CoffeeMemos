package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.search.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.use_case.SortRecipeUseCase
import javax.inject.Inject

class SortRecipeInteractor @Inject constructor() : SortRecipeUseCase {
    override fun sort(
        sortType: RecipeSortType,
        list: List<SearchRecipeModel>
    ): List<SearchRecipeModel> {
        val result = when(sortType) {
            RecipeSortType.OLD -> {
                list.sortedBy { recipe -> recipe.recipeId}
            }
            RecipeSortType.NEW -> {
                list.sortedByDescending { recipe -> recipe.recipeId }
            }
            RecipeSortType.ROAST -> {
              list.sortedByDescending { recipe -> recipe.roast }
            }
            RecipeSortType.GRIND_SIZE ->  {
                list.sortedBy { recipe -> recipe.grindSize }
            }
            RecipeSortType.RATING ->  {
                list.sortedByDescending { recipe -> recipe.rating }
            }
            RecipeSortType.SOUR -> {
                list.sortedByDescending { recipe -> recipe.sour }
            }
            RecipeSortType.BITTER -> {
                list.sortedByDescending { recipe -> recipe.bitter }
            }
            RecipeSortType.SWEET -> {
                list.sortedByDescending { recipe -> recipe.sweet }
            }
            RecipeSortType.FLAVOR -> {
                list.sortedByDescending { recipe -> recipe.flavor }
            }
            RecipeSortType.RICH -> {
                list.sortedByDescending { recipe -> recipe.rich }
            }
        }

        return result
    }
}