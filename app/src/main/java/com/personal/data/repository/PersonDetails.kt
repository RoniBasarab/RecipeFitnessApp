package com.personal.data.repository

import com.personal.utils.FitnessStatus


data class PersonDetails (
     var BMI: String? = null,
     var BMR: String? = null,
     var gender: String? = null,
     var age: Int = 0,
     var height: Int = 0,
     var weight: Int = 0,
     var fitnessStatus: FitnessStatus = FitnessStatus.UNINITIALIZED
)