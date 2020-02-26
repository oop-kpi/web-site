package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.JsonView
import com.oopwebsite.entity.view.View
import org.springframework.data.mongodb.core.mapping.DBRef
import javax.persistence.Id

data class LaboratoryWork(
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
        var name:String = "",
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
        var pathToFile:String = "",
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
        var mark:Int = 0,
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
        var comments:List<Comment> = listOf(),
        @JsonView(View.EVALUATION::class)
        @DBRef
        var user:User? = null){
        @Id
        @JsonView(View.EVALUATION::class)
        var id: String? = null
}