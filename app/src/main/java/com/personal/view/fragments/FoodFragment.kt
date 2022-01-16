package com.personal.view.fragments
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.annotation.MenuRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.personal.R
import com.personal.adapter.FoodAdapter
import com.personal.data.repository.RecipeRepository
import com.personal.databinding.FragmentFoodBinding
import com.personal.utils.ProgressBarAnimation
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

      private fun setupFragment()
    {
        binding.rvFoods.isVisible = false
        animateProgressBar(binding.pbLoadingBar)


        setOnClickListeners()
        foodAdapter = FoodAdapter(RecipeRepository(), "Sunday")
        FoodViewModel = ViewModelProvider(this).get(FoodFragmentViewModel::class.java)
        FoodViewModel.makeAPICall(foodAdapter,binding.pbLoadingBar)

        binding.rvFoods.adapter = foodAdapter
        binding.rvFoods.layoutManager = LinearLayoutManager(this.context)
        binding.rvFoods.visibility = View.VISIBLE
    }

    private fun animateProgressBar(progress: ProgressBar)
    {
        val anim = ProgressBarAnimation(progress, 500f, 1000f)
        anim.duration = 1000
        progress.startAnimation(anim)
    }

    private fun setOnClickListeners()
    {
        binding.btnBackToPersonDetails.setOnClickListener {
            findNavController().navigate(R.id.personDetailsFragment)
        }

        binding.btnDoneMeals.setOnClickListener {
            findNavController().navigate(R.id.doneFragment)
        }

        binding.txtChooseWeekDay.setOnClickListener {
            showMenu(binding.linearlayoutWeekPicker,R.menu.week_menu)
        }

    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this.context, v)

        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            binding.txtChooseWeekDay.text = menuItem.title
            foodAdapter.uncheckMealsOnNewDayPick()

            true
        }
        popup.setOnDismissListener {
            binding.txtChooseWeekDay.setTypeface(null, Typeface.BOLD)
            foodAdapter.updateDayOfWeek(binding.txtChooseWeekDay.text.toString())
        }
        // Show the popup menu.
        popup.show()
    }
}