package com.personal.data.service
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.personal.BuildConfig
import com.personal.data.model.RecipesApiResponse
import retrofit2.http.GET


interface FoodService {


    @GET("api/recipes/v2?type=public&q=meat&app_id=${BuildConfig.API_ID}&app_key=${BuildConfig.API_KEY}")
    suspend fun getMeatRecipes(): RecipesApiResponse

    @GET("api/recipes/v2?type=public&q=fish&app_id=${BuildConfig.API_ID}&app_key=${BuildConfig.API_KEY}")
    suspend fun getFishRecipes(): RecipesApiResponse

    @GET("api/recipes/v2?type=public&q=dairy&app_id=${BuildConfig.API_ID}6&app_key=${BuildConfig.API_KEY}")
    suspend fun getDairyRecipes(): RecipesApiResponse

}