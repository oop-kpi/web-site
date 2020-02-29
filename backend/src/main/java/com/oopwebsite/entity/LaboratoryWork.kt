package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.JsonView
import com.mongodb.lang.NonNull
import com.oopwebsite.entity.view.View
import org.springframework.data.mongodb.core.mapping.DBRef
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class LaboratoryWork(
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
        var name:String? = null,
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
        @JsonView(View.EVALUATION::class,View.LABORATORY_WORK::class,View.FULL_INFORMATION::class)
        var id: String? = null
}