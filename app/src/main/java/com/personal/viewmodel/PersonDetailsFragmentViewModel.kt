package com.personal.viewmodel
import androidx.lifecycle.ViewModel
import com.personal.utils.FitnessStatus
import com.personal.view.fragments.PersonDetailsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonDetailsFragmentViewModel : ViewModel()
{

    fun calcBMI(kg: String, m: String): String {
        return (kg.toDouble() / Math.pow(m.toDouble()/100.0 ,2.0)).toString()

    }

    fun calcCalorieIntakeNeeded(fitnessStatus: FitnessStatus, gender: String, kg: Int, height: Int, age: Int): Double {
        val BMRmale = 66 + (6.3 * (kg*2.20462)) + (12.9 * (height*0.393701)) - (6.8 * age)
        val BMRfemale = 655 + (4.3 * (kg*2.20462)) + (4.7 * (height*0.393701)) - (4.7 * age)

        when(gender) {
            "Male" ->
                when (fitnessStatus) {
                    FitnessStatus.SEDENTARY ->
                        return BMRmale*1.2
                    FitnessStatus.LIGHTLY ->
                        return BMRmale*1.375
                    FitnessStatus.MODERATELY ->
                        return BMRmale*1.55
                    FitnessStatus.VERY ->
                        return BMRmale*1.725
                }
            "Female" ->
                when (fitnessStatus) {
                    FitnessStatus.SEDENTARY ->
                    return BMRfemale*1.2
                    FitnessStatus.LIGHTLY ->
                    return BMRfemale*1.375
                    FitnessStatus.MODERATELY ->
                    return BMRfemale*1.375
                    FitnessStatus.VERY ->
                    return BMRfemale*1.375
                }
        }
        return 0.0
    }
}