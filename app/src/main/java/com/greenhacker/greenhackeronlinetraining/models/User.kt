package com.greenhacker.greenhackeronlinetraining.models

class User {

    var id: Int = 0
        private set
    var name: String? = null
        private set
    var email: String? = null
        private set
    var phone: String = ""
    var password: String? = null
        private set
    var job: String = ""
    var location: String = ""
    var category_id: String = ""
    var updated_at: String = ""
    var created_at: String = ""
    var gender: String? = null
        private set

    constructor(name: String, email: String, phone: String, password: String, job: String, location: String, category_id: String, updated_at: String, created_at: String) {
        this.name = name
        this.email = email
        this.phone = phone
        this.password = password
        this.job = job
        this.location = location
        this.category_id = category_id
        this.updated_at = updated_at
        this.created_at = created_at
    }

    constructor(name: String, email: String, password: String, gender: String) {
        this.name = name
        this.email = email
        this.password = password
        this.gender = gender
    }

    constructor(id: Int, name: String, email: String, gender: String) {
        this.id = id
        this.name = name
        this.email = email
        this.gender = gender
    }

    constructor(id: Int, name: String, email: String, password: String, gender: String) {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
        this.gender = gender
    }

    constructor(i: Int, nothing: Nothing?, email: String, password: String)
}