package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.JsonView
import com.oopwebsite.entity.view.View
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Document
data class LaboratoryWork(
        @NotNull
        @NotBlank(message = "Name cant be blank!")
        @Size(min=3)
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
        var name:String? = null,
        @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
        @NotNull
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

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as LaboratoryWork

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id?.hashCode() ?: 0
        }


}