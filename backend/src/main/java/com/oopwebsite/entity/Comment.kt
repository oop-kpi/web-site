package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.JsonView
import com.oopwebsite.entity.view.View
import javax.persistence.Id

data class Comment(
        @JsonView(View.LABORATORY_WORK::class,View.FULL_INFORMATION::class)
        var content:String = "",
        @JsonView(View.LABORATORY_WORK::class,View.FULL_INFORMATION::class)
        var owner:User? = null){
    @Id
    var id: String = ""
}