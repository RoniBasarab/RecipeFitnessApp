package com.personal.viewmodel
import android.telephony.SmsMessage
import androidx.lifecycle.ViewModel
import com.personal.data.repository.PersonDetails
import com.personal.utils.FitnessStatus
import com.personal.view.fragments.PersonDetailsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonDetailsFragmentViewModel : ViewModel()
{
    var BMI: String? = null
    var BMR: String? = null
    var gender: String? = null
    var age: Int = 0
    var height: Int = 0
    var weight: Int = 0
    var fitnessStatus: FitnessStatus = FitnessStatus.UNINITIALIZED


    fun calcBMI(kg: String, m: String): String {
        BMI = ((kg.toDouble() / Math.pow(m.toDouble()/100.0 ,2.0)).toString())
        PersonDetails.BMI = BMI
        return BMI as String

    }

    fun calcCalorieIntakeNeeded(fitnessStatus: FitnessStatus, gender: String, kg: Int, height: Int, age: Int): String? {
        val BMRmale = 66 + (6.3 * (kg*2.20462)) + (12.9 * (height*0.393701)) - (6.8 * age)
        val BMRfemale = 655 + (4.3 * (kg*2.20462)) + (4.7 * (height*0.393701)) - (4.7 * age)

        this.age = age
        this.height = height
        this.weight = kg
        this.gender = gender
        this.fitnessStatus = fitnessStatus

        when(gender) {
            "Male" ->
                when (fitnessStatus) {
                    FitnessStatus.SEDENTARY -> {
                        PersonDetails.BMR = BMR
                        BMR = (BMRmale*1.2).toString()
                        return BMR
                    }
                    FitnessStatus.LIGHTLY -> {
                        PersonDetails.BMR = BMR
                        BMR = (BMRmale*1.375).toString()
                        return BMR
                    }

                    FitnessStatus.MODERATELY ->{
                        PersonDetails.BMR = BMR
                        BMR = (BMRmale*1.55).toString()
                        return BMR
                    }

                    FitnessStatus.VERY ->{
                        PersonDetails.BMR = BMR
                        BMR = (BMRmale*1.725).toString()
                        return BMR

                    }
                    else -> {return "0.0"}
                }
            "Female" ->
                when (fitnessStatus) {

                    FitnessStatus.SEDENTARY -> {
                        PersonDetails.BMR = BMR
                        BMR = (BMRfemale*1.2).toString()
                        return BMR
                    }
                    FitnessStatus.LIGHTLY -> {
                        PersonDetails.BMR = BMR
                        BMR = (BMRfemale*1.375).toString()
                        return BMR
                    }

                    FitnessStatus.MODERATELY ->{
                        PersonDetails.BMR = BMR
                        BMR = (BMRfemale*1.55).toString()
                        return BMR
                    }

                    FitnessStatus.VERY ->{
                        PersonDetails.BMR = BMR
                        BMR = (BMRfemale*1.725).toString()
                        return BMR

                    }
                    else -> {return "0.0"}
                }
        }
        return ""
    }

     fun isDataInitialized(): Boolean {
        if((PersonDetails.BMI.isNullOrEmpty() &&
            PersonDetails.BMR.isNullOrEmpty() &&
            PersonDetails.gender.isNullOrEmpty() &&
            PersonDetails.age.equals(0) &&
            PersonDetails.height.equals(0) &&
            PersonDetails.weight.equals(0) &&
            PersonDetails.fitnessStatus.equals(FitnessStatus.UNINITIALIZED)
        ))
        {
            return false
        }
        return true
    }
}