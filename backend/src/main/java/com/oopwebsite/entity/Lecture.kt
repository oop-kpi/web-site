package com.oopwebsite.entity

import javax.persistence.Id

data class Lecture(var path:String = "", var name:String = "", var description:String = ""){
    @Id
    var id:String? = null
}