package com.personal.view.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.personal.R
import com.personal.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // Valid for OnCreateView and OnDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater,container,false)
        setupFragment()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupFragment()
    {
        setupOnClickListeners()
    }

    fun setupOnClickListeners()
    {
        binding.cbYesNoEmail.setOnClickListener {
            if(!binding.cbYesNoEmail.isChecked)
            {
                binding.linearlayoutEmailInput.visibility = View.INVISIBLE
                val animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_out)
                binding.linearlayoutEmailInput.startAnimation(animation)
            }
            else
            {
                binding.linearlayoutEmailInput.visibility = View.VISIBLE
                val animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)
                binding.linearlayoutEmailInput.startAnimation(animation)
            }
        }

        binding.btnCreateYourDiet.setOnClickListener {
            findNavController().navigate(R.id.personDetailsFragment)
        }
    }
}