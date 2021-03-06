package com.personal.viewmodel
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import com.personal.BuildConfig
import com.personal.adapter.FoodAdapter
import com.personal.data.repository.Recipe
import com.personal.data.repository.RecipeRepository
import com.personal.data.repository.SuccessfulRecipeHitsRepository
import com.personal.data.service.FoodService
import com.personal.utils.Converters
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = BuildConfig.BASE_URL
val logger = "logger"

class FoodFragmentViewModel : ViewModel()
{
    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(FoodService::class.java)

    private val localFoodRepository: RecipeRepository = RecipeRepository()

    private suspend fun getFoodRecipes()
    {

        withContext(Dispatchers.IO)
        {
            try {
                var response = retrofitBuilder.getMeatRecipes()
                SuccessfulRecipeHitsRepository.numberOfRecipes += response.count
                SuccessfulRecipeHitsRepository.recipiesList.addAll(response.hits)

                response = retrofitBuilder.getFishRecipes()
                SuccessfulRecipeHitsRepository.numberOfRecipes += response.count
                SuccessfulRecipeHitsRepository.recipiesList.addAll(response.hits)

                UpdateLocalRepoLists()

            }catch (e: Exception)
            {
                Log.d(logger, e.toString())
                SuccessfulRecipeHitsRepository.numberOfRecipes = 0
                SuccessfulRecipeHitsRepository.recipiesList = mutableListOf()
            }
        }
    }

     @SuppressLint("NotifyDataSetChanged")
     private fun loadRecipes(adapter: FoodAdapter, loadingVisibility: ProgressBar){
        val scope = CoroutineScope(Dispatchers.IO)
         scope.launch {
             getFoodRecipes()
             adapter.foodList = localFoodRepository
             GlobalScope.launch(Dispatchers.Main) {
                 loadingVisibility.visibility = View.GONE
                 adapter.notifyDataSetChanged()
             }
         }
    }

    fun makeAPICall(adapter: FoodAdapter, loadingVisibility: ProgressBar){
        loadRecipes(adapter,loadingVisibility)

    }

    private fun UpdateLocalRepoLists() {
        for((i, hit) in SuccessfulRecipeHitsRepository.recipiesList.withIndex())
        {
            val curRecipe = Recipe( i+1,
                hit.recipe.label,
                hit.recipe.cuisineType[0],
                hit.recipe.calories,
                hit.recipe.image,
                Converters.listToJson(hit.recipe.ingredientLines),
                false
            )
            localFoodRepository.recipes.add(curRecipe)
        }
    }
}