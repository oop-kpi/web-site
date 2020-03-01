package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.JsonView
import com.mongodb.lang.NonNull
import com.oopwebsite.entity.view.View
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import javax.persistence.Id
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
@Document
data class User(@JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
                @NotBlank
                var email:String ="",
                     @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
                     @NotBlank
                var name: String = "",
                     @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
                var ball:Int = 0,
                     var password:String = "",
                     @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
                     @NotBlank
                var login:String = "",
                     @JsonView(View.FULL_INFORMATION::class)
                var roles: Set<Role> = setOf(),
                     @JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class,View.EVALUATION::class)
                     @NotNull
                var group: Group? = null ){
    @DBRef
    @JsonView(View.FULL_INFORMATION::class)
    var laboratoryWorks:Set<LaboratoryWork> = setOf()
@Id
@JsonView(View.FULL_INFORMATION::class,View.LABORATORY_WORK::class)
var id: String? = null

    override fun equals(other: Any?): Boolean {
        if (!(other is User)) return false;
        return this.id.equals(other.id)
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }
}