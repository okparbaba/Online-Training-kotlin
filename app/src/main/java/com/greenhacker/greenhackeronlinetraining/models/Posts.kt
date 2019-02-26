package com.greenhacker.greenhackeronlinetraining.models

class Posts {
    var id: String? = null

    var postTitle: String? = null

    var postBody: String? = null

    var url: String? = null

    override fun toString(): String {
        return "Posts{" +
                "id='" + id + '\''.toString() +
                ", postTitle='" + postTitle + '\''.toString() +
                ", postBody='" + postBody + '\''.toString() +
                ", url='" + url + '\''.toString() +
                '}'.toString()
    }
}

