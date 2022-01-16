package com.personal.data.repository

import com.personal.utils.FitnessStatus


object PersonDetails {
     var BMI: String? = null
     var BMR: String? = null
     var gender: String? = null
     var age: Int = 0
     var height: Int = 0
     var weight: Int = 0
     var fitnessStatus: FitnessStatus = FitnessStatus.UNINITIALIZED

     fun dataIsInitialized(): Boolean{
          if(!(BMI.isNullOrEmpty()&&
                  BMR.isNullOrEmpty()&&
                  gender.isNullOrEmpty()&&
                  age.equals(0)&&
                  height.equals(0)&&
                  weight.equals(0)&&
                  fitnessStatus.equals(FitnessStatus.UNINITIALIZED)))
          {
               return true
          }
          return false
     }
}