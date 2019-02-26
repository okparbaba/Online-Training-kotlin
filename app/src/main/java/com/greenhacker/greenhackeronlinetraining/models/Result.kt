package com.greenhacker.greenhackeronlinetraining.models

import com.google.gson.annotations.SerializedName


class Result(@field:SerializedName("result")
             val result: String, @field:SerializedName("user")
             val user: User)