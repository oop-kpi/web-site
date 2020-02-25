package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.*
import com.oopwebsite.entity.view.View
import org.springframework.data.mongodb.core.mapping.DBRef
import javax.persistence.Id

data class Comment(
        @JsonView(View.EVALUATION::class)
        var content:String = ""
//        @JsonView(View.EVALUATION::class)
//        var owner:User? = null
){
    @Id
    var id: String = ""
}