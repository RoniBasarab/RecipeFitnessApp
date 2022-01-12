package com.personal.data.service
import com.personal.data.model.RecipesApiResponse
import retrofit2.http.GET


interface FoodService {
    @GET("api/recipes/v2?type=public&q=meat&app_id=c03a10e6&app_key=6a022d801e29f6d474badad2c8c5daed")
    suspend fun getRecipes(): RecipesApiResponse

}