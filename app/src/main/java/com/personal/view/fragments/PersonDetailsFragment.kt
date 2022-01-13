package com.personal.view.fragments
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.personal.R
import com.personal.databinding.FragmentPersonDetailsBinding
import com.personal.utils.FitnessStatus
import com.personal.viewmodel.PersonDetailsFragmentViewModel


class PersonDetailsFragment : Fragment() {

    private var _binding: FragmentPersonDetailsBinding? = null
    private lateinit var personDetailsViewModel: PersonDetailsFragmentViewModel

    // Valid for OnCreateView and OnDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPersonDetailsBinding.inflate(inflater,container,false)
        setupFragment()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupFragment()
    {
        binding.rbFemale.isChecked = false
        binding.rbMale.isChecked = false
        personDetailsViewModel = ViewModelProvider(this).get(PersonDetailsFragmentViewModel::class.java)
        setupOnClickListeners()
    }

    private fun setupOnClickListeners()
    {
        binding.rbMale.setOnClickListener {
            binding.rbFemale.isChecked = false
            binding.rbMale.isChecked = true
        }

        binding.rbFemale.setOnClickListener {
            binding.rbFemale.isChecked = true
            binding.rbMale.isChecked = false
        }

        binding.txtChosenFitnessLevel.setOnClickListener {
            showMenu(binding.linearlayoutFitnessLevel,R.menu.popup_menu)
        }

        binding.btnCalculateBMI.setOnClickListener {
            if(!(!binding.rbMale.isChecked && !binding.rbFemale.isChecked && binding.etHeightInput.text.isNullOrEmpty() && binding.etWeightInput.text.isNullOrEmpty() && binding.etAgeInput.text.isNullOrEmpty()))
            {
                val age = binding.etAgeInput.text.toString().toInt()
                val height = binding.etHeightInput.text.toString().toInt()
                val weight = binding.etWeightInput.text.toString().toInt()
                val gender: String
                var fitnessStatus: FitnessStatus = FitnessStatus.SEDENTARY
                val fitnessStatusChosen = binding.txtChosenFitnessLevel.text.toString()


                if(binding.rbMale.isChecked)
                    gender = binding.rbMale.text.toString()
                else
                    gender = binding.rbFemale.text.toString()

                when(fitnessStatusChosen)
                {
                    "Sedentary" -> fitnessStatus = FitnessStatus.SEDENTARY
                    "Lightly Active" -> fitnessStatus = FitnessStatus.LIGHTLY
                    "Moderately Active" -> fitnessStatus = FitnessStatus.MODERATELY
                    "Very Active" -> fitnessStatus = FitnessStatus.VERY
                }

                binding.txtBMIResult.text = personDetailsViewModel.calcBMI(weight.toString(), height.toString())
                binding.txtCalorieIntakeAllowed.text = personDetailsViewModel.calcCalorieIntakeNeeded(fitnessStatus,gender,weight,height,age)
                    .toString()
                binding.txtCalorieIntakeAllowed.setTextColor(Color.BLACK)
                binding.txtCalorieIntakeAllowed.visibility = View.VISIBLE
            }
            else
            {

                binding.txtCalorieIntakeAllowed.setTextColor(Color.RED)
                binding.txtCalorieIntakeAllowed.text = "FILL FORM"
                binding.txtCalorieIntakeAllowed.visibility = View.VISIBLE
            }
        }

        binding.btnPressToRecyclerView.setOnClickListener {
            findNavController().navigate(R.id.foodFragment)
        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this.context, v)

        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            binding.txtChosenFitnessLevel.text = menuItem.title
            true
        }
        popup.setOnDismissListener {
            binding.txtChosenFitnessLevel.setTypeface(null, Typeface.BOLD)
            binding.txtChosenFitnessLevel.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
        // Show the popup menu.
        popup.show()
    }

}