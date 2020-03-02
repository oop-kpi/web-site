package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.*
import com.oopwebsite.entity.view.View
import javax.persistence.Id

data class Comment(
        @JsonView(View.EVALUATION::class,View.FULL_INFORMATION::class)
        var content:String = "",
        @JsonView(View.EVALUATION::class,View.FULL_INFORMATION::class)
        var owner:String? = null
){
    @Id
    var id: String = ""
}