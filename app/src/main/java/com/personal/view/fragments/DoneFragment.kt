package com.personal.view.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import com.personal.R
import com.personal.data.repository.PersonDetails
import com.personal.data.repository.WeekMealSchedule
import com.personal.databinding.FragmentDoneBinding
import com.personal.databinding.FragmentFoodBinding
import com.personal.utils.Converters
import com.personal.viewmodel.logger
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.lang.Exception
import java.util.*


class DoneFragment : Fragment() {

    private var _binding: FragmentDoneBinding? = null
    // Valid for OnCreateView and OnDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDoneBinding.inflate(inflater,container,false)

        setupFragment()

        return binding.root
    }

    private fun setupFragment() {

        setOnClickListeners()
    }

    private fun setOnClickListeners() {

        binding.btnShareWhatsapp.setOnClickListener {

            try {
                val filename = "Weekly Meals.csv"
                val myPath = File(System.getProperty("user.dir"), "mypath")
                if (!myPath.exists()) {
                    myPath.mkdir()
                }
                val myFile = File(myPath, filename)

                // write the file here, e.g.
                FileOutputStream(myFile).use { stream ->
                    stream.write(myCsvString.toByteArray())
                }

                // here, com.example.myapp.fileprovider should match the file provider in your manifest
                val contentUri = this.context?.let { it1 -> getUriForFile(it1, "android.support.FILE_PROVIDER_PATHS", myFile) }

                val intent = Intent(Intent.ACTION_SEND)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                intent.setDataAndType(
                    contentUri,
                    "text/csv")
                intent.putExtra(Intent.EXTRA_STREAM, contentUri);
                startActivity(intent)
            } catch (e: Exception) {
                Log.d(logger, "Writing csv failed: $e")
                val toast = Toast.makeText(this.context, "Writing csv failed: $e", Toast.LENGTH_LONG)
                toast.show()
            }

        }

        binding.btnGoogleCalendar.setOnClickListener {

        }
    }

}