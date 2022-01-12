package com.personal.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.personal.adapter.FoodAdapter
import com.personal.data.repository.RecipeRepository
import com.personal.databinding.FragmentFoodBinding
import com.personal.viewmodel.FoodFragmentViewModel
import com.personal.viewmodel.logger


class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private lateinit var  FoodViewModel: FoodFragmentViewModel
    private lateinit var foodAdapter: FoodAdapter

    // Valid for OnCreateView and OnDestroyView
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodBinding.inflate(inflater,container,false)

        setupFragment()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(logger,"OnDestroyView() - recycler")
        _binding = null
    }

      fun setupFragment()
    {
        foodAdapter = FoodAdapter(RecipeRepository())
        FoodViewModel = ViewModelProvider(this).get(FoodFragmentViewModel::class.java)
        FoodViewModel.makeAPICall(foodAdapter)


        binding.rvFoods.adapter = foodAdapter
        binding.rvFoods.layoutManager = LinearLayoutManager(this.context)



    }
}