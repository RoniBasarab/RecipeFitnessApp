package com.personal.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.personal.R
import com.personal.data.repository.Recipe
import com.personal.data.repository.RecipeRepository
import com.personal.databinding.ItemFoodBinding
import com.personal.viewmodel.logger
import java.util.*


class FoodAdapter(
    var foodList: RecipeRepository
)  : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>()
{

    class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(recipe: Recipe)
        {
            binding.tvFoodTitle.text = recipe.recipeName
            binding.tvCuisine.text = recipe.cuisineType
            binding.tvFoodCalorie.text = recipe.calories.toString()
            Glide.with(binding.root)
                .load(recipe.image)
                .into(binding.imgFoodImage)

            binding.cbChosen.setOnCheckedChangeListener {_, isChecked ->
                if(isChecked)
                {
                    binding.root.setBackgroundResource(R.drawable.textview_black_border)
                }
                else
                {
                    binding.root.setBackgroundColor(0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList.recipes[position])
    }

    override fun getItemCount(): Int {
        return foodList.recipes.size
    }
}