package com.personal.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.personal.data.repository.Recipe
import com.personal.data.repository.RecipeRepository
import com.personal.data.repository.WeekMealSchedule
import com.personal.databinding.ItemFoodBinding
import com.personal.viewmodel.logger

@GlideModule
class FoodAdapter(
    var foodList: RecipeRepository,
    var dayOfWeek: String
)  : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>()
{
    var totalMealCalories: Double = 0.0


    class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(recipe: Recipe)
        {
            itemView.apply {

                binding.cbChosen.isChecked = recipe.expanded

                recipe.expanded = binding.cbChosen.isChecked
                binding.tvFoodTitle.text = recipe.recipeName
                binding.tvCuisine.text = recipe.cuisineType
                binding.tvFoodCalorie.text = recipe.calories.toString()
                Glide.with(binding.root)
                    .load(recipe.image)
                    .into(binding.imgFoodImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        Log.d(logger,position.toString())

        holder.bind(foodList.recipes[position])

        holder.itemView.setOnClickListener {
            foodList.recipes[position].expanded = !foodList.recipes[position].expanded
            notifyItemChanged(position)

            if (foodList.recipes[position].expanded) {
                totalMealCalories += foodList.recipes[position].calories
                addOrRemoveRecipeFromWeek(foodList.recipes[position],"add")
            }
            else {
                totalMealCalories -= foodList.recipes[position].calories
                addOrRemoveRecipeFromWeek(foodList.recipes[position],"remove")
            }

        }
    }

    override fun getItemCount(): Int {
        return foodList.recipes.size
    }

    private fun addOrRemoveRecipeFromWeek(recipe: Recipe, action: String) {
        when(action)
        {
            "add" -> {
                WeekMealSchedule.week.getOrPut(dayOfWeek, ::mutableListOf).add(recipe)
            }
            "remove" -> {
                if(WeekMealSchedule.week[dayOfWeek] != null)
                    WeekMealSchedule.week[dayOfWeek]?.remove(recipe)
            }
        }
    }

    fun updateDayOfWeek(day: String)
    {
        this.dayOfWeek = day
    }

    @SuppressLint("NotifyDataSetChanged")
    fun uncheckMealsOnNewDayPick()
    {
        for(recipe in foodList.recipes)
        {
            if(recipe.expanded)
            {
                recipe.expanded = false

            }
        }
        notifyDataSetChanged()
    }
}