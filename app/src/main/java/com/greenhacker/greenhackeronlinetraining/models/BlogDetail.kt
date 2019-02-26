package com.greenhacker.greenhackeronlinetraining.models

class BlogDetail {
    private val id: String? = null
    var title: String? = null
    var description: String? = null
    var admin_name: String? = null
    var imageUrl: String? = null

    override fun toString(): String {
        return "BlogDetail{" +
                "title='" + title + '\''.toString() +
                ", description='" + description + '\''.toString() +
                ", admin_name='" + admin_name + '\''.toString() +
                '}'.toString()
    }
}
