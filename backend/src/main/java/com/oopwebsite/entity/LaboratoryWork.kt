package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.JsonView
import com.oopwebsite.entity.view.View
import javax.persistence.Id

data class LaboratoryWork(
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
        var name:String = "",
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
        var pathToFile:String = "",
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
        var mark:Int = 0,
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
        var comments:List<Comment> = listOf(),
        var user:User? = null){
        @Id
        var id: String? = null
}