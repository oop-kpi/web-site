package com.oopwebsite.entity

import com.fasterxml.jackson.annotation.JsonView
import com.oopwebsite.entity.view.View
import org.springframework.data.mongodb.core.mapping.DBRef
import javax.persistence.Id

data class User(@JsonView(View.FULL_INFORMATION::class)
                var email:String ="",
                @JsonView(View.FULL_INFORMATION::class)
                var name: String = "",
                @JsonView(View.FULL_INFORMATION::class)
                var ball:Int = 0,
                var password:String = "",
                @JsonView(View.FULL_INFORMATION::class)
                var login:String = "",
                @JsonView(View.FULL_INFORMATION::class)
                var roles: Set<Role> = setOf(),
                @JsonView(View.FULL_INFORMATION::class)
                var group: Group = Group.IB91,
                @JsonView(View.FULL_INFORMATION::class)
                @DBRef
                var laboratoryWorks:Set<LaboratoryWork> = setOf()){
@Id
@JsonView(View.FULL_INFORMATION::class)
var id: String? = null
}